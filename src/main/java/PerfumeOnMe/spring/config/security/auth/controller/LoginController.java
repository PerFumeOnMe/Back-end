package PerfumeOnMe.spring.config.security.auth.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.config.security.auth.dto.AuthRequestDTO;
import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.config.security.auth.service.LoginService;
import PerfumeOnMe.spring.web.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	@Operation(
		summary = "로그인 API",
		description = "사용자의 아이디와 비밀번호로 로그인을 진행하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.SignupResult.class))),
		}
	)
	public ResponseEntity<ApiResponse<AuthResponseDTO.LoginResult>> login(
		@RequestBody @Valid AuthRequestDTO.Login loginRequest, HttpServletResponse response) throws IOException {
		AuthResponseDTO.LoginResult loginResult = loginService.login(loginRequest, response);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(loginResult));
	}
}
