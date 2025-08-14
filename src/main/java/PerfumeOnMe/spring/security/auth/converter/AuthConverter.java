package PerfumeOnMe.spring.security.auth.converter;

import PerfumeOnMe.spring.common.enums.Social;
import PerfumeOnMe.spring.security.auth.dto.AuthResponseDTO;

public class AuthConverter {

	public static AuthResponseDTO.LoginResult toLoginResult(String refreshToken, Long userId, Social social) {
		return AuthResponseDTO.LoginResult.builder()
			.refreshToken(refreshToken)
			.userId(userId)
			.social(social)
			.build();
	}

}
