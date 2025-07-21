package PerfumeOnMe.spring.web.dto.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDTO {

	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SignupResult {
		private Long userId;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MyPageProfileResponse {
		private String nickName;
		private String imageUrl;
		private List<String> preferredNotes;
	}
}
