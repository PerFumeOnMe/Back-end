package PerfumeOnMe.spring.apiPayload;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.code.BaseCode;
import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.code.status.SuccessStatus;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

	private static final ObjectMapper mapper = new ObjectMapper();
	@JsonProperty("isSuccess")
	private final Boolean isSuccess;
	private final String code;
	private final String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	// 200 OK
	public static <T> ApiResponse<T> onSuccess(T result) {
		return new ApiResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
	}

	// 201 CREATED, ...
	public static <T> ApiResponse<T> of(BaseCode code, T result) {
		return new ApiResponse<>(true, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(),
			result);
	}

	// 400 CLIENT, 500 SERVER 등 실패한 경우 응답 생성
	public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
		return new ApiResponse<>(false, code, message, data);
	}

	// Security Filter 레벨에서 사용하는 ErrorResponse 생성 메서드
	public static void setErrorResponse(HttpServletResponse response,
		ErrorStatus code, Throwable e) throws IOException {

		// 응답 헤더 작성
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.setStatus(code.getReasonHttpStatus().getHttpStatus().value());

		// 응답 데이터 생성 및 작성
		ApiResponse<String> res = ApiResponse
			.onFailure(code.getCode(), code.getMessage(), null);
		response.getWriter().write(mapper.writeValueAsString(res));
	}
}
