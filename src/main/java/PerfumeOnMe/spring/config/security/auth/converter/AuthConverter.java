package PerfumeOnMe.spring.config.security.auth.converter;

import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;

public class AuthConverter {

	public static AuthResponseDTO.LoginResult toLoginResult(String refreshToken, Long userId) {
		return AuthResponseDTO.LoginResult.builder()
			.refreshToken(refreshToken)
			.userId(userId)
			.build();
	}

}
