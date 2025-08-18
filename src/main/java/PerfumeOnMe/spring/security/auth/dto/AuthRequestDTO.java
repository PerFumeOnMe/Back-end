package PerfumeOnMe.spring.security.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDTO {

	@Getter
	@NoArgsConstructor
	public static class Login {
		@NotNull
		@Schema(description = "사용자가 입력한 아이디", example = "umc123")
		private String loginId;
		@NotNull
		@Schema(description = "사용자가 입력한 비밀번호", example = "asdf1234")
		private String password;
	}
}
