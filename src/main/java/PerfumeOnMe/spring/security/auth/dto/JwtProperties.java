package PerfumeOnMe.spring.security.auth.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/*
application.yml에 설정해둔 값을 담아오기 위한 DTO
 */
@Component
@Getter
@Setter
@ConfigurationProperties("jwt.token")
public class JwtProperties {

	private String secretKey;
	private Expiration expiration;

	@Getter
	@Setter
	public static class Expiration {
		private Long access;
		private Long refresh;
	}
}
