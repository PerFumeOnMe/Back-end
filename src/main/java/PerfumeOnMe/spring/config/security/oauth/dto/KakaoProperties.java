package PerfumeOnMe.spring.config.security.oauth.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties("spring.security.oauth2.kakao")
public class KakaoProperties {

	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String authorizationUri;
	private String tokenUri;
	private String userInfoUri;
	private String userNameAttribute;
	private List<String> scopes;
	private String authorizationGrantType;
}
