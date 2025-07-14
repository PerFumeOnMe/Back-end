package PerfumeOnMe.spring.config.security.auth.converter;

import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.domain.enums.Social;

public class AuthConverter {

	public static AuthResponseDTO.LoginResult toLoginResult(String refreshToken, Long userId, Social social) {
		return AuthResponseDTO.LoginResult.builder()
			.refreshToken(refreshToken)
			.userId(userId)
			.social(social)
			.build();
	}

}
