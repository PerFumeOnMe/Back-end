package PerfumeOnMe.spring.config.security.auth.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import PerfumeOnMe.spring.config.security.auth.provider.JwtTokenProvider;
import PerfumeOnMe.spring.config.security.auth.token.JwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
요청에서 토큰을 추출해 유효성을 검증하고,
Authentication을 SecurityContextHolder에 설정하는 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String accessToken = jwtTokenProvider.resolveToken(request);

		if (StringUtils.hasText(accessToken)) {
			String loginId = jwtTokenProvider.getSubject(accessToken);
			JwtAuthenticationToken authRequest = new JwtAuthenticationToken(loginId, accessToken);
			Authentication authResult = authenticationManager.authenticate(authRequest);
			SecurityContextHolder.getContext().setAuthentication(authResult);
		}

		filterChain.doFilter(request, response);
	}
}
