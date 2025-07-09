package PerfumeOnMe.spring.service.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.config.security.auth.manager.LogoutAccessTokenManager;
import PerfumeOnMe.spring.config.security.auth.manager.RefreshTokenManager;
import PerfumeOnMe.spring.config.security.auth.provider.JwtTokenProvider;
import PerfumeOnMe.spring.config.security.auth.token.JwtAuthenticationToken;
import PerfumeOnMe.spring.converter.UserConverter;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.web.dto.user.UserRequestDTO;
import PerfumeOnMe.spring.web.dto.user.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenManager refreshTokenManager;
	private final LogoutAccessTokenManager logoutAccessTokenManager;
	private final UserDetailsService userDetailsService;

	// 사용자 회원가입
	@Override
	public UserResponseDTO.SignupResult signup(UserRequestDTO.Signup request) {

		// RequestDTO 값 추출하기
		String name = request.getName();
		String loginId = request.getLoginId();
		String password = request.getPassword();

		/*
		비즈니스 로직 검증 - loginId 중복 확인
		 */
		Optional<User> findUser = userRepository.findUserByLoginId(loginId);
		if (findUser.isPresent()) {
			throw new GeneralException(ErrorStatus.LOGIN_ID_DUPLICATE);
		}

		// 사용자 정보 엔티티 변환 및 DB 저장
		User newUser = UserConverter
			.toSignupUser(name, loginId, passwordEncoder.encode(password));
		userRepository.save(newUser);

		// 사용자 회원가입 결과를 ResponseDTO로 응답
		return UserConverter.toSignupResult(newUser);
	}

	// 리프레시 토큰으로 액세스 토큰과 리프레시 토큰 재발급
	@Override
	public AuthResponseDTO.LoginResult reissue(String reqRefreshToken, HttpServletResponse response) {

		// 리프레시 토큰에서 Subject 추출
		String loginId = jwtTokenProvider.getSubject(reqRefreshToken);

		// 토큰 생성 및 DTO에 담기
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
		JwtAuthenticationToken request = new JwtAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		String accessToken = jwtTokenProvider.createAccessToken(request);
		String refreshToken = jwtTokenProvider.createRefreshToken(request);
		AuthResponseDTO.LoginResult loginResultDTO = AuthResponseDTO.LoginResult.builder()
			.refreshToken(refreshToken)
			.build();

		// 새로 발급한 리프레시 토큰을 Redis에 저장 - 덮어씌우기
		refreshTokenManager.saveRefreshToken(loginId, refreshToken);

		// 응답 헤더 작성
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Authorization", "Bearer " + accessToken);

		return loginResultDTO;
	}

	// 사용자 로그아웃 - 액세스 토큰과 리프레시 토큰 블랙리스트화
	@Override
	public String logout(HttpServletRequest request) {

		// 요청에서 액세스 토큰 추출 및 유효성 검증
		String accessToken = jwtTokenProvider.resolveToken(request);
		jwtTokenProvider.validateToken(accessToken);

		// 토큰에서 loginId 추출 및 사용자 검증
		String loginId = jwtTokenProvider.getSubject(accessToken);
		userDetailsService.loadUserByUsername(loginId);

		// 액세스 토큰 블랙리스트화
		logoutAccessTokenManager.saveLogoutAccessToken(loginId, accessToken);

		// 리프레시 토큰 삭제
		if (refreshTokenManager.findRefreshToken(loginId)) {
			refreshTokenManager.deleteRefreshToken(loginId);
		}

		return loginId;
	}

	// 회원탈퇴 - 로그아웃 진행 후 사용자 삭제
	@Override
	public void deleteUser(HttpServletRequest request) {
		String loginId = logout(request);
		Optional<User> findUser = userRepository.findUserByLoginId(loginId);
		findUser.ifPresent(userRepository::delete);
	}
}
