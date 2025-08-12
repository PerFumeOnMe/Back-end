package PerfumeOnMe.spring.security.auth.manager;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import PerfumeOnMe.spring.security.auth.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

/*
리프레시 토큰을 Redis에 저장, 수정, 삭제를 담당하는 클래스
 */
@Component
@RequiredArgsConstructor
public class RefreshTokenManager {

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
