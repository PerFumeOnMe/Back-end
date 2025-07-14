package PerfumeOnMe.spring.config.security.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import PerfumeOnMe.spring.config.security.auth.token.JwtAuthenticationToken;
import PerfumeOnMe.spring.domain.enums.Social;
import lombok.RequiredArgsConstructor;

/*
JWT 인증 시 사용되는 AuthenticationProvider
Subject와 토큰을 검증하고, 성공하면 Authentication 반환
JwtAuthenticationToken만 지원할 수 있음
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = authentication.getCredentials().toString();
		String loginId = jwtTokenProvider.getSubject(token);

		UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);

		return new JwtAuthenticationToken(userDetails, null, userDetails.getAuthorities(), Social.LOCAL);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
