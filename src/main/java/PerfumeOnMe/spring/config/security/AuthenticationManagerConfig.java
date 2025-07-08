package PerfumeOnMe.spring.config.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import PerfumeOnMe.spring.config.security.auth.provider.CustomLoginAuthenticationProvider;
import PerfumeOnMe.spring.config.security.auth.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;

/*
SecurityConfig와의 빈 순환 참조를 방지하기 위해 클래스 분리
 */
@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig {

	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final CustomLoginAuthenticationProvider customLoginAuthenticationProvider;

	// AuthenticationManager 빈 등록
	@Bean
	public AuthenticationManager authenticationManager() {
		List<AuthenticationProvider> providers = List.of(
			jwtAuthenticationProvider,
			customLoginAuthenticationProvider);
		return new ProviderManager(providers);
	}

}
