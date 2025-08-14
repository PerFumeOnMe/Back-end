package PerfumeOnMe.spring.security.auth.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
사용자의 권한으로 접근할 수 없는 API를 호출한 경우 터지는 예외를 핸들링하는 클래스
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {

		// 응답 헤더 작성
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		// 응답 데이터 생성 및 작성
		ApiResponse<String> res = ApiResponse.onFailure(ErrorStatus._FORBIDDEN.getCode(),
			ErrorStatus._FORBIDDEN.getMessage(), "권한이 없습니다.");
		response.getWriter().write(mapper.writeValueAsString(res));
	}
}
