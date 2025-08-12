package PerfumeOnMe.spring.security.auth.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
액세스 토큰(인증)이 필요한 API을 그냥 호출한 경우 터지는 예외를 핸들링하는 클래스
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {

		// 응답 헤더 설정
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		// 응답 데이터 생성 및 작성
		ApiResponse<String> res = ApiResponse.onFailure(ErrorStatus._UNAUTHORIZED.getCode(),
			ErrorStatus._UNAUTHORIZED.getMessage(), "액세스 토큰을 입력해 주세요.");
		response.getWriter().write(mapper.writeValueAsString(res));
	}
}
