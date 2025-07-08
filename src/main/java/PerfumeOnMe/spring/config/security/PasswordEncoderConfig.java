package PerfumeOnMe.spring.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
SecurityConfig와의 빈 순환 참조를 방지하기 위해 클래스 분리
 */
@Configuration
public class PasswordEncoderConfig {

	// 문자열 암호화를 위한 PasswordEncoder 빈 등록
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
