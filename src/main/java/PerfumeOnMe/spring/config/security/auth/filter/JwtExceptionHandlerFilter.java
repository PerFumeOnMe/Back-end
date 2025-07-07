package PerfumeOnMe.spring.config.security.auth.filter;

import static PerfumeOnMe.spring.apiPayload.ApiResponse.*;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
Security Filter에서 발생하는 예외를 잡아 통일해둔 API 응답에 맞게 처리하는 클래스
 */
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (GeneralException e) {
			setErrorResponse(response, e.getErrorStatus(), e);
		} catch (UsernameNotFoundException e) {
			setErrorResponse(response, ErrorStatus.LOGIN_ID_NOT_FOUND, e);
		} catch (BadCredentialsException e) {
			setErrorResponse(response, ErrorStatus.PASSWORD_NOT_MATCH, e);
		} catch (Exception e) {
			setErrorResponse(response, ErrorStatus._INTERNAL_SERVER_ERROR, e);
		}
	}
}
