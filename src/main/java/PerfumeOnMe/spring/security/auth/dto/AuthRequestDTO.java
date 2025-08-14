package PerfumeOnMe.spring.security.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDTO {

	@Getter
	@NoArgsConstructor
	public static class Login {
		@NotNull
		private String loginId;
		@NotNull
		private String password;
	}
}
