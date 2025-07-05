package PerfumeOnMe.spring.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.apiPayload.code.status.SuccessStatus;
import PerfumeOnMe.spring.service.user.UserService;
import PerfumeOnMe.spring.web.dto.user.UserRequestDTO;
import PerfumeOnMe.spring.web.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4002", description = "비밀번호 확인을 실패했습니다."),
		}
	)
	public ResponseEntity<ApiResponse<UserResponseDTO.SignupResult>> signup(
		@RequestBody @Valid UserRequestDTO.Signup request) {
		UserResponseDTO.SignupResult result = userService.signup(request);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._CREATED, result), HttpStatus.CREATED);
	}
}
