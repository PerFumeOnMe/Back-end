package PerfumeOnMe.spring.config.security.oauth.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import PerfumeOnMe.spring.config.security.auth.converter.AuthConverter;
import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.config.security.auth.manager.LogoutAccessTokenManager;
import PerfumeOnMe.spring.config.security.auth.manager.RefreshTokenManager;
import PerfumeOnMe.spring.config.security.auth.provider.JwtTokenProvider;
import PerfumeOnMe.spring.config.security.auth.token.JwtAuthenticationToken;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.config.security.oauth.converter.OAuthConverter;
import PerfumeOnMe.spring.config.security.oauth.dto.KakaoResponseDTO;
import PerfumeOnMe.spring.config.security.oauth.util.KakaoClient;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.enums.Social;
import PerfumeOnMe.spring.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService implements OAuthService {

	private final KakaoClient kakaoClient;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;
	private final RefreshTokenManager refreshTokenManager;
	private final LogoutAccessTokenManager logoutAccessTokenManager;

	@Override
	public AuthResponseDTO.LoginResult oAuthLogin(String code, HttpServletResponse response) {

		// 카카오 토큰 요청
		KakaoResponseDTO.Token token = kakaoClient.requestToken(code);

		// 카카오 사용자 정보 요청
		KakaoResponseDTO.UserInfo userInfo = kakaoClient.requestUserInfo(token.getAccess_token());
		String name = (userInfo.getKakao_account().getName());
		String email = userInfo.getKakao_account().getEmail();
		String nickname = userInfo.getKakao_account().getProfile().getNickname();
		String imageUrl = userInfo.getKakao_account().getProfile().getProfile_image_url();

		// 이미 가입한 사용자라면 꺼내고, 아니라면 회원가입 진행
		User user = userRepository.findUserByLoginId(email).orElseGet(() -> {
			User newUser = OAuthConverter.toSignupUser(
				Social.KAKAO, "kakao" + email, name, "password", imageUrl, nickname);
			return userRepository.save(newUser);
		});

		// 사용자의 로그아웃 액세스 토큰이 존재하는 경우 삭제
		if (logoutAccessTokenManager.findLogoutAccessToken(user.getLoginId())) {
			logoutAccessTokenManager.deleteLogoutAccessToken(user.getLoginId());
		}

		// 사용자 JWT 인증 및 토큰 발급
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLoginId());
		Long userId = ((CustomUserDetails)userDetails).getUserId();
		JwtAuthenticationToken request = new JwtAuthenticationToken(
			userDetails, null, userDetails.getAuthorities(), Social.KAKAO);
		String accessToken = jwtTokenProvider.createAccessToken(request);
		String refreshToken = jwtTokenProvider.createRefreshToken(request);
		AuthResponseDTO.LoginResult loginResultDTO = AuthConverter.toLoginResult(refreshToken, userId, Social.KAKAO);

		// 새로 발급한 리프레시 토큰을 Redis에 저장
		refreshTokenManager.saveRefreshToken(email, refreshToken);

		// 액세스 토큰 헤더 설정 및 응답 DTO 반환
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Authorization", "Bearer " + accessToken);

		// SecurityContextHolder에 인증 설정
		SecurityContextHolder.getContext().setAuthentication(request);

		return loginResultDTO;
	}

	@Override
	public Social getProvider() {
		return Social.KAKAO;
	}
}
