package PerfumeOnMe.spring.converter;

import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.web.dto.user.UserResponseDTO;

public class UserConverter {

	// 사용자 회원가입을 위한 엔티티로 변환
	public static User toSignupUser(String name, String loginId, String password) {
		return User.builder()
			.name(name)
			.loginId(loginId)
			.password(password)
			.build();
	}

	// 사용자를 회원가입 결과 DTO 반환
	public static UserResponseDTO.SignupResult toSignupResult(User user) {
		return UserResponseDTO.SignupResult.builder()
			.userId(user.getId())
			.build();
	}
}
