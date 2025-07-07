package PerfumeOnMe.spring.config.security.auth.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

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
