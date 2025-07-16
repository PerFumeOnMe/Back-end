package PerfumeOnMe.spring.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.config.security.auth.converter.AuthConverter;
import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.config.security.auth.manager.LogoutAccessTokenManager;
import PerfumeOnMe.spring.config.security.auth.manager.RefreshTokenManager;
import PerfumeOnMe.spring.config.security.auth.provider.JwtTokenProvider;
import PerfumeOnMe.spring.config.security.auth.token.JwtAuthenticationToken;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.converter.UserConverter;
import PerfumeOnMe.spring.converter.UserNoteConverter;
import PerfumeOnMe.spring.domain.Note;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.enums.Social;
import PerfumeOnMe.spring.domain.mapping.UserNote;
import PerfumeOnMe.spring.repository.note.NoteRepository;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.repository.userNote.UserNoteRepository;
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
	private final UserNoteRepository userNoteRepository;
	private final NoteRepository noteRepository;

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

		if (reqRefreshToken == null || reqRefreshToken.isBlank()) {
			throw new GeneralException(ErrorStatus.REFRESH_TOKEN_NOT_FOUND);
		}

		// 리프레시 토큰에서 Subject 추출
		String loginId = jwtTokenProvider.getSubject(reqRefreshToken);

		// 토큰 생성 및 DTO에 담기
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
		Social social = ((CustomUserDetails)userDetails).getSocial();
		JwtAuthenticationToken request = new JwtAuthenticationToken(
			userDetails, null, userDetails.getAuthorities(), social);
		String accessToken = jwtTokenProvider.createAccessToken(request);
		String refreshToken = jwtTokenProvider.createRefreshToken(request);
		Long userId = ((CustomUserDetails)userDetails).getUserId();
		AuthResponseDTO.LoginResult loginResultDTO = AuthConverter.toLoginResult(refreshToken, userId, social);

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

	// 온보딩 요청 정보 설정 메서드
	public void saveUserNote(User user, List<Long> noteCategoryIdList) {
		noteCategoryIdList.forEach(noteCategoryId -> {
			Note note = noteRepository.findById(noteCategoryId)
				.orElseThrow(() -> new GeneralException(ErrorStatus.INVALID_NOTE_ID));
			UserNote userNote = UserNoteConverter.toUserNote(note);
			userNoteRepository.save(userNote);
			user.addUserNote(userNote); // 양방향 연관관계만 설정
		});
	}

	// 온보딩
	@Override
	public void onboarding(UserRequestDTO.Onboarding request, CustomUserDetails userDetails) {

		// 닉네임 중복 검증
		if (userRepository.findUserByNickname(request.getNickname()).isPresent()) {
			throw new GeneralException(ErrorStatus.NICKNAME_DUPLICATE);
		}

		// 사용자 조회
		User findUser = userRepository.findUserByLoginId(userDetails.getUsername())
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		// 온보딩 요청 정보 설정 - nickname, imageURL, gender, age
		findUser.onboarding(request);

		// 온보딩 요청 정보 설정 - noteCategoryId
		saveUserNote(findUser, request.getNoteCategoryId());
	}

	// 사용자 선호 향 수정
	@Override
	public void updateUserNote(UserRequestDTO.UserNoteUpdate request, CustomUserDetails userDetails) {

		// 사용자 조회
		User findUser = userRepository.findUserByLoginId(userDetails.getUsername())
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		// 사용자 선호 노트 정보 삭제
		userNoteRepository.deleteAllByUser(findUser);
		findUser.getUserNoteList().clear();

		// 온보딩 요청 정보 설정 - noteCategoryId
		saveUserNote(findUser, request.getNoteCategoryId());
	}
}
