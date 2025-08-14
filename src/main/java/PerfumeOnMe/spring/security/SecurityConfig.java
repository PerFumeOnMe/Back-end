package PerfumeOnMe.spring.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import PerfumeOnMe.spring.security.auth.filter.JwtAuthenticationFilter;
import PerfumeOnMe.spring.security.auth.filter.JwtExceptionHandlerFilter;
import PerfumeOnMe.spring.security.auth.filter.JwtLoginFilter;
import PerfumeOnMe.spring.security.auth.handler.JwtAccessDeniedHandler;
import PerfumeOnMe.spring.security.auth.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	// 인증 여부를 확인하지 않을 경로 지정
	public static final String[] AUTH_WHITELIST = {
		"/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html", "/swagger-ui/**",
		"/swagger/**", "/users/signup", "/auth/login", "/auth/social/**", "/users/reissue",
		"/health", "/fragrances/allow/**", "/favicon.ico", "/images/**",
		"/css/**", "/js/**", "/webjars/**"
	};
	private final JwtAuthenticationFilter JwtAuthenticationFilter;
	private final JwtExceptionHandlerFilter JwtExceptionHandlerFilter;
	private final JwtAuthenticationEntryPoint JwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler JwtAccessDeniedHandler;
	private final JwtLoginFilter jwtLoginFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// 요청 경로별 인증 확인 설정
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(AUTH_WHITELIST).permitAll()
				.anyRequest().authenticated() // 개발 진행할 때 임시로 풀어두기 -> 나중에 authenticated()로 변경
			)
			// filter 레벨에서 발생하는 예외 핸들러 설정
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(JwtAuthenticationEntryPoint)
				.accessDeniedHandler(JwtAccessDeniedHandler))
			// filter 추가
			// .addFilterAt(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class) // 로그인 필터 제거
			.addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(JwtExceptionHandlerFilter, JwtAuthenticationFilter.class)
			// Session 관련 설정 - 소셜 로그인 과정에서 필요할까봐 IF_REQUIRED로 설정
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
			// 자동 로그인 페이지, Basic 로그인, CSRF 비활성화
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			// CORS에 아래에서 등록한 빈 설정
			.cors(cors -> cors.configurationSource(corsConfigurationSource()));

		return http.build();
	}

	// CORS 설정 및 빈 등록
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:5000", "http://52.198.172.96:8080",
			"http://localhost:5173",
			"https://api.perfumeonme.p-e.kr", "https://perfumeonme.vercel.app"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
		config.setAllowedHeaders(List.of("Authorization", "Refresh-Token", "Content-Type"));
		config.setAllowCredentials(true);
		config.setExposedHeaders(List.of("Authorization", "Refresh-Token", "Content-Type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
