package PerfumeOnMe.spring.config.security.auth.token;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

/*
JWT 인증용 AuthenticationToken
 */
@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private final Object principal;
	private final Object credentials;

	public JwtAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
		this.principal = principal;
		this.credentials = credentials;
	}

	// GrantedAuthority를 포함한 생성자를 만들어야 신뢰할 수 있는, 인증된 토큰이 됨
	public JwtAuthenticationToken(UserDetails userDetails, Object o,
		Collection<? extends GrantedAuthority> authorities) {
		super(userDetails, null, authorities);
		this.principal = userDetails;
		this.credentials = null;
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
