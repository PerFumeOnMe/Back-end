package PerfumeOnMe.spring.user.converter;

import java.util.List;
import java.util.stream.Collectors;

import PerfumeOnMe.spring.user.domain.User;
import PerfumeOnMe.spring.user.web.dto.UserResponseDTO;

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

	// 사용자의 프로필 조회 DTO 반환
	public static UserResponseDTO.MyPageProfileResponse toMyPageProfileResponse(User user) {
		List<String> preferredNotes = user.getUserNoteList().stream()
			.map(userNote -> userNote.getNote().getName())
			.collect(Collectors.toList());

		return UserResponseDTO.MyPageProfileResponse.builder()
			.nickName(user.getNickname())
			.imageUrl(user.getImageURL())
			.preferredNotes(preferredNotes)
			.build();
	}
}
