package PerfumeOnMe.spring.security.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.common.enums.Social;
import PerfumeOnMe.spring.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.security.oauth.docs.OAuthControllerDocs;
import PerfumeOnMe.spring.security.oauth.service.OAuthService;
import PerfumeOnMe.spring.security.oauth.service.OAuthServiceFactory;
import PerfumeOnMe.spring.security.oauth.util.OAuthProviderResolver;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/social")
public class OAuthController implements OAuthControllerDocs {

	private final OAuthServiceFactory serviceFactory;

	@PostMapping("/{provider}")
	public ResponseEntity<ApiResponse<AuthResponseDTO.LoginResult>> oAuthLogin(
		@RequestParam("code") String code,
		@PathVariable("provider") String provider,
		HttpServletResponse response) {

		// provider에 맞는 OAuthService 얻기
		Social social = OAuthProviderResolver.resolve(provider);
		OAuthService oAuthService = serviceFactory.getOAuthService(social);

		// 결과 얻기
		AuthResponseDTO.LoginResult result = oAuthService.oAuthLogin(code, response);

		return ResponseEntity.ok().body(ApiResponse.onSuccess(result));
	}

	// 인가 코드 확인용 임시 컨트롤러
	// @GetMapping("/{provider}")
	public ResponseEntity<ApiResponse<String>> getCode(
		@RequestParam("code") String code,
		@PathVariable("provider") String Provider,
		HttpServletResponse response) {
		return ResponseEntity.ok(ApiResponse.onSuccess(code));
	}
}
