package PerfumeOnMe.spring.security.oauth.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import PerfumeOnMe.spring.common.enums.Social;
import PerfumeOnMe.spring.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.security.auth.manager.LogoutAccessTokenManager;
import PerfumeOnMe.spring.security.auth.manager.RefreshTokenManager;
import PerfumeOnMe.spring.security.auth.provider.JwtTokenProvider;
import PerfumeOnMe.spring.security.auth.service.LoginService;
import PerfumeOnMe.spring.security.auth.token.JwtAuthenticationToken;
import PerfumeOnMe.spring.security.oauth.converter.OAuthConverter;
import PerfumeOnMe.spring.security.oauth.dto.KakaoResponseDTO;
import PerfumeOnMe.spring.security.oauth.util.KakaoClient;
import PerfumeOnMe.spring.user.domain.User;
import PerfumeOnMe.spring.user.repository.UserRepository;
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
	private final LoginService loginService;

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
				Social.KAKAO, email, name, "password", imageUrl, nickname);
			return userRepository.save(newUser);
		});

		// 사용자의 로그아웃 액세스 토큰이 존재하는 경우 삭제
		if (logoutAccessTokenManager.findLogoutAccessToken(user.getLoginId())) {
			logoutAccessTokenManager.deleteLogoutAccessToken(user.getLoginId());
		}

		// 사용자 JWT 인증 및 토큰 발급
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLoginId());
		JwtAuthenticationToken request = new JwtAuthenticationToken(
			userDetails, null, userDetails.getAuthorities(), Social.KAKAO);

		// SecurityContextHolder에 인증 설정
		SecurityContextHolder.getContext().setAuthentication(request);

		return loginService.generateAuthResponse(email, request, Social.KAKAO, response);
	}

	@Override
	public Social getProvider() {
		return Social.KAKAO;
	}
}
