package PerfumeOnMe.spring.config.security.auth.userDetails;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

/*
사용자 식별 정보
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final Long userId;
	private final String name;
	private final String loginId;
	private final String password;

	@Override
	public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return loginId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}
}
