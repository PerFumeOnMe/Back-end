package PerfumeOnMe.spring.config.security.auth.userDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

/*
사용자 식별 정보를 조회하는 서비스
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		User user = userRepository.findUserByLoginId(loginId)
			.orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));

		return new CustomUserDetails(
			user.getId(), user.getName(), user.getLoginId(), user.getPassword());
	}
}
