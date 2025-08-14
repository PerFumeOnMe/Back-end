package PerfumeOnMe.spring.security.oauth.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.security.auth.dto.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;

public interface OAuthControllerDocs {

	@Operation(
		summary = "소셜 로그인 API",
		description = "소셜 액세스 토큰을 발급하고, 해당 토큰으로 사용자 정보를 가져와 회원가입 및 로그인을 진행하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4003", description = "해당 아이디를 가진 사용자가 존재하지 않습니다."),
		},
		parameters = {
			@Parameter(name = "code", description = "인가 코드가 필요합니다."),
			@Parameter(name = "provider", description = "예시: kakao")
		}
	)
	ResponseEntity<ApiResponse<AuthResponseDTO.LoginResult>> oAuthLogin(
		@RequestParam("code") String code,
		@PathVariable("provider") String provider,
		HttpServletResponse response);

	ResponseEntity<ApiResponse<String>> getCode(
		@RequestParam("code") String code,
		@PathVariable("provider") String Provider,
		HttpServletResponse response);
}