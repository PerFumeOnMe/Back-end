package PerfumeOnMe.spring.security.oauth.converter;

import PerfumeOnMe.spring.common.enums.Social;
import PerfumeOnMe.spring.user.domain.User;

public class OAuthConverter {

	// 소셜 로그인 동의 후 회원가입
	public static User toSignupUser(Social social, String email, String name,
		String password, String imageUri, String nickname) {
		return User.builder()
			.social(social)
			.name(name)
			.loginId(email)
			.password(password)
			.imageURL(imageUri)
			.nickname(nickname)
			.build();
	}
}
