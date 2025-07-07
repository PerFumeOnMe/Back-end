package PerfumeOnMe.spring.config.security.auth.repository;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import PerfumeOnMe.spring.config.security.auth.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

	private static final String REFRESH_TOKEN_PREFIX = "RT:";
	private final StringRedisTemplate stringRedisTemplate;
	private final JwtTokenProvider jwtTokenProvider;

	public void saveRefreshToken(String loginId, String refreshToken) {
		long expirationMillis = jwtTokenProvider.getExpiration(refreshToken);
		long nowMillis = System.currentTimeMillis();
		long durationMillis = expirationMillis - nowMillis;

		String key = REFRESH_TOKEN_PREFIX + loginId;
		stringRedisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(durationMillis));
	}

	public boolean findRefreshToken(String loginId) {
		String key = REFRESH_TOKEN_PREFIX + loginId;
		return stringRedisTemplate.hasKey(key);
	}

	public String getRefreshToken(String loginId) {
		String key = REFRESH_TOKEN_PREFIX + loginId;
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void deleteRefreshToken(String loginId) {
		String key = REFRESH_TOKEN_PREFIX + loginId;
		stringRedisTemplate.delete(key);
	}
}
