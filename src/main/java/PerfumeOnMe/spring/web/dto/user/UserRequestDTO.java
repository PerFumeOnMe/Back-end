package PerfumeOnMe.spring.web.dto.user;

import java.util.List;

import PerfumeOnMe.spring.validation.annotation.ExistUserAge;
import PerfumeOnMe.spring.validation.annotation.ExistUserGender;
import PerfumeOnMe.spring.validation.annotation.ValidUserNote;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDTO {

	// 회원가입
	@Getter
	@NoArgsConstructor
	public static class Signup {
		@NotBlank
		@Schema(description = "사용자가 입력한 이름", example = "홍길동")
		@Pattern(
			regexp = "^[가-힣]+$",
			message = "한글만 입력할 수 있으며, 공백 없이 1자 이상 입력해주세요."
		)
		private String name;
		@NotBlank
		@Schema(description = "사용자가 입력한 아이디", example = "umc123")
		@Pattern(
			regexp = "^[a-z0-9]+$",
			message = "영어 소문자와 숫자만 입력할 수 있으며, 공백 없이 1자 이상 입력해주세요."
		)
		private String loginId;
		@NotBlank
		@Schema(description = "사용자가 입력한 비밀번호", example = "asdf1234")
		@Pattern(
			regexp = "^[A-Za-z\\d@$!%*?&#]{8,20}$",
			message = "비밀번호는 영어 대소문자, 숫자, 특수문자(@$!%*?&#)만 허용되며, 공백 없이 8자 이상 20자 이하로 입력해주세요."
		)
		private String password;
	}

	// 온보딩
	@Getter
	@NoArgsConstructor
	public static class Onboarding {
		@NotBlank
		@Schema(description = "사용자가 입력한 닉네임", example = "리버")
		@Pattern(
			regexp = "^[가-힣a-zA-Z0-9]{2,10}$",
			message = "닉네임은 한글, 영어 대소문자, 숫자만 허용되며, 공백 없이 2자 이상 10자 이하로 입력해주세요."
		)
		private String nickname;
		@Schema(description = "사용자가 설정한 사진 URL", example = "https://...")
		private String imageURL;
		@NotNull
		@Schema(description = "사용자가 설정한 성별", example = "FEMALE")
		@ExistUserGender
		private String gender;
		@NotNull
		@Schema(description = "사용자가 설정한 연령대", example = "TWENTIES")
		@ExistUserAge
		private String age;
		@NotNull
		@Schema(description = "사용자가 설정한 선호하는 향", example = "[5,11,2]")
		@ValidUserNote
		private List<Long> noteCategoryId;
	}
}
