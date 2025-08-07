package PerfumeOnMe.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

	/*
	RedisConnectionFactory의 구현체로 LettuceConnectionFactory 사용 및 빈 등록
	Spring Boot 자동 설정을 사용하기 위해 주석 처리
	 */
	// @Bean
	// public RedisConnectionFactory redisConnectionFactory() {
	// 	return new LettuceConnectionFactory();
	// }

	/*
	Key-Value를 String-String으로 저장하는 StringRedisTemplate 빈 등록
	 */
	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		return new StringRedisTemplate(redisConnectionFactory);
	}
}
