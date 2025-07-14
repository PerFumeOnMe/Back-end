package PerfumeOnMe.spring.config.security.auth.token;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import PerfumeOnMe.spring.domain.enums.Social;
import lombok.Getter;

/*
JWT 인증용 AuthenticationToken
 */
@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private final Object principal;
	private final Object credentials;
	private final Social social;

	// 인증 전
	public JwtAuthenticationToken(Object principal, Object credentials, Social social) {
		super(principal, credentials);
		this.principal = principal;
		this.credentials = credentials;
		this.social = social;
	}

	// 인증 후
	// GrantedAuthority를 포함한 생성자를 만들어야 신뢰할 수 있는, 인증된 토큰이 됨
	public JwtAuthenticationToken(UserDetails userDetails, Object o,
		Collection<? extends GrantedAuthority> authorities, Social social) {
		super(userDetails, null, authorities);
		this.principal = userDetails;
		this.credentials = null;
		this.social = social;
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
