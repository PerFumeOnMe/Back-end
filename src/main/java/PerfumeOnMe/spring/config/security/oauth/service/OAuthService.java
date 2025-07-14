package PerfumeOnMe.spring.config.security.oauth.service;

import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.domain.enums.Social;
import jakarta.servlet.http.HttpServletResponse;

/*
다양한 소셜 로그인 방식을 위한 인터페이스
 */
public interface OAuthService {
	AuthResponseDTO.LoginResult oAuthLogin(String code, HttpServletResponse response);

	Social getProvider();
}
