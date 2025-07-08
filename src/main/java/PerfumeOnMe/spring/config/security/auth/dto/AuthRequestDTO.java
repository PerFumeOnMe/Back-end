package PerfumeOnMe.spring.config.security.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDTO {

	@Getter
	@NoArgsConstructor
	public static class Login {
		private String loginId;
		private String password;
	}
}
