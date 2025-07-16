package PerfumeOnMe.spring.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.apiPayload.code.status.SuccessStatus;
import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.service.user.UserService;
import PerfumeOnMe.spring.web.dto.user.UserRequestDTO;
import PerfumeOnMe.spring.web.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User", description = "사용자 CRUD API")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	@Operation(
		summary = "자체 회원가입 API",
		description = "사용자의 이름, 아이디, 비밀번호, 비밀번호 확인 값을 입력받아 회원가입하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON201", description = "리소스를 성공적으로 생성했습니다.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.SignupResult.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "이미 사용된 아이디입니다."),
		}
	)
	public ResponseEntity<ApiResponse<UserResponseDTO.SignupResult>> signup(
		@RequestBody @Valid UserRequestDTO.Signup request) {
		UserResponseDTO.SignupResult result = userService.signup(request);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._CREATED, result), HttpStatus.CREATED);
	}

	@PostMapping("/reissue")
	@Operation(
		summary = "토큰 재발급 API",
		description = "헤더에 입력한 Refresh-Token으로 새로운 액세스 토큰과 리프레시 토큰을 발급하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4003", description = "해당 아이디를 가진 사용자가 존재하지 않습니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "TOKEN4002", description = "해당 리프레시 토큰이 존재하지 않습니다.")
		}
	)
	public ResponseEntity<ApiResponse<AuthResponseDTO.LoginResult>> reissue(
		@RequestHeader(name = "Refresh-Token") String refreshToken, HttpServletResponse response) {
		AuthResponseDTO.LoginResult result = userService.reissue(refreshToken, response);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(result));
	}

	@PostMapping("/logout")
	@Operation(
		summary = "로그아웃 API",
		description = "사용자의 액세스 토큰과 리프레시 토큰을 블랙리스트화하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4003", description = "해당 아이디를 가진 사용자가 존재하지 않습니다."),
		}
	)
	public ResponseEntity<ApiResponse<Object>> logout(HttpServletRequest request) {
		userService.logout(request);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
	}

	@DeleteMapping("/me")
	@Operation(
		summary = "회원탈퇴 API",
		description = "사용자의 액세스 토큰과 리프레시 토큰을 블랙리스트화하고, 사용자를 삭제하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4003", description = "해당 아이디를 가진 사용자가 존재하지 않습니다."),
		}
	)
	public ResponseEntity<ApiResponse<Object>> deleteUser(HttpServletRequest request) {
		userService.deleteUser(request);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
	}

	@PostMapping("/onboarding")
	@Operation(
		summary = "온보딩 API",
		description = "사용자의 닉네임, 프로필 사진, 성별, 연령대, 선호하는 향 리스트를 입력받아 저장하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4003", description = "해당 아이디를 가진 사용자가 존재하지 않습니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4006", description = "이미 사용된 닉네임입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FILTER4003", description = "유효하지 않은 노트 ID 입니다."),
		}
	)
	public ResponseEntity<ApiResponse<Object>> onboarding(@Valid @RequestBody UserRequestDTO.Onboarding request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		userService.onboarding(request, userDetails);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
	}
}
