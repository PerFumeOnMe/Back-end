package PerfumeOnMe.spring.config.security.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private final Object principal;
	private final Object credentials;

	public JwtAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	public JwtAuthenticationToken(UserDetails userDetails) {
		super(userDetails, null);
		this.principal = userDetails;
		this.credentials = null;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return super.getCredentials();
	}

	@Override
	public Object getPrincipal() {
		return super.getPrincipal();
	}
}
