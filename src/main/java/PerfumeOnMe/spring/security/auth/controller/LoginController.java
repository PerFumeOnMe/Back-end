package PerfumeOnMe.spring.security.auth.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.security.auth.docs.LoginControllerDocs;
import PerfumeOnMe.spring.security.auth.dto.AuthRequestDTO;
import PerfumeOnMe.spring.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.security.auth.service.LoginService;
import PerfumeOnMe.spring.user.web.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController implements LoginControllerDocs {

	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponseDTO.LoginResult>> login(
		@RequestBody @Valid AuthRequestDTO.Login loginRequest, HttpServletResponse response) throws IOException {
		AuthResponseDTO.LoginResult loginResult = loginService.login(loginRequest, response);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(loginResult));
	}
}
