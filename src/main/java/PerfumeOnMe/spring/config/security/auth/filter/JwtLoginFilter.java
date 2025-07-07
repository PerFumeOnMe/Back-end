package PerfumeOnMe.spring.config.security.auth.filter;

import static PerfumeOnMe.spring.apiPayload.ApiResponse.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.config.security.auth.dto.AuthRequestDTO;
import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.config.security.auth.provider.JwtTokenProvider;
import PerfumeOnMe.spring.config.security.auth.repository.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
/auth/login 경로로 요청이 들어오면,
로그인 검증과 토큰 발급을 진행하고
Authentication을 SecurityContextHolder에 설정하는 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	private final ObjectMapper mapper = new ObjectMapper();

	// AuthenticationManager 주입 및 처리할 URL 설정
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
		setFilterProcessesUrl("/auth/login");
	}

	// 인증 시도
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		try {
			AuthRequestDTO.Login requestDTO = mapper.readValue(request.getInputStream(), AuthRequestDTO.Login.class);
			UsernamePasswordAuthenticationToken loginRequest = UsernamePasswordAuthenticationToken
				.unauthenticated(requestDTO.getLoginId(), requestDTO.getPassword());
			return this.getAuthenticationManager().authenticate(loginRequest);
		} catch (IOException e) {
			throw new GeneralException(ErrorStatus.LOGIN_PARSING_FAIL);
		}
	}

	// 인증 시도에 성공하면 실행되는 메서드
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain, Authentication authResult) throws IOException, ServletException {

		// 토큰 생성 및 DTO에 담기
		String loginId = authResult.getName();
		String accessToken = jwtTokenProvider.createAccessToken(authResult);
		String refreshToken = jwtTokenProvider.createRefreshToken(authResult);
		AuthResponseDTO.RefreshToken refreshTokenDTO = AuthResponseDTO
			.RefreshToken.builder()
			.refreshToken(refreshToken)
			.build();

		// 리프레시 토큰을 Redis에 저장
		refreshTokenRepository.saveRefreshToken(loginId, refreshToken);

		// 응답 헤더 작성
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Authorization", "Bearer " + accessToken);

		// 응답 데이터 생성 및 작성
		ApiResponse<AuthResponseDTO.RefreshToken> res = ApiResponse.onSuccess(refreshTokenDTO);
		response.getWriter().write(mapper.writeValueAsString(res));

		// SecurityContextHolder에 인증 설정
		SecurityContextHolder.getContext().setAuthentication(authResult);
	}

	// 인증 시도에 실패하면 실행되는 메서드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {

		if (failed instanceof BadCredentialsException) {
			setErrorResponse(response, ErrorStatus.PASSWORD_NOT_MATCH, failed);
		} else if (failed instanceof UsernameNotFoundException) {
			setErrorResponse(response, ErrorStatus.LOGIN_ID_NOT_FOUND, failed);
		} else { // 예외 분기 처리 더 해야 함
			setErrorResponse(response, ErrorStatus.LOGIN_UNKNOWN_ERROR, failed);
		}
	}
}
