package PerfumeOnMe.spring.config.security.auth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import PerfumeOnMe.spring.config.security.auth.token.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;

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

		return new JwtAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
