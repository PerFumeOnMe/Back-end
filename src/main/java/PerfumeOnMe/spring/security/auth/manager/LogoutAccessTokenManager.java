package PerfumeOnMe.spring.security.auth.manager;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import PerfumeOnMe.spring.security.auth.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LogoutAccessTokenManager {

	private static final String LOGOUT_ACCESS_TOKEN_PREFIX = "Logout:";
	private final StringRedisTemplate stringRedisTemplate;
	private final JwtTokenProvider jwtTokenProvider;

	public void saveLogoutAccessToken(String loginId, String accessToken) {
		long expirationMillis = jwtTokenProvider.getExpiration(accessToken);
		long nowMillis = System.currentTimeMillis();
		long durationMillis = expirationMillis - nowMillis;

		String key = LOGOUT_ACCESS_TOKEN_PREFIX + loginId;
		stringRedisTemplate.opsForValue().set(key, accessToken, Duration.ofMillis(durationMillis));
	}

	public boolean findLogoutAccessToken(String loginId) {
		String key = LOGOUT_ACCESS_TOKEN_PREFIX + loginId;
		return stringRedisTemplate.hasKey(key);
	}

	public String getLogoutAccessToken(String loginId) {
		String key = LOGOUT_ACCESS_TOKEN_PREFIX + loginId;
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void deleteLogoutAccessToken(String loginId) {
		String key = LOGOUT_ACCESS_TOKEN_PREFIX + loginId;
		stringRedisTemplate.delete(key);
	}
}
