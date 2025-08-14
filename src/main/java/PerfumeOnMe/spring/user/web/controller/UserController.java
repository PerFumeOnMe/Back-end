package PerfumeOnMe.spring.user.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.apiPayload.code.status.SuccessStatus;
import PerfumeOnMe.spring.fragrance.web.dto.FragranceRequestDTO;
import PerfumeOnMe.spring.fragrance.web.dto.FragranceResponseDTO;
import PerfumeOnMe.spring.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.user.service.UserService;
import PerfumeOnMe.spring.user.web.docs.UserControllerDocs;
import PerfumeOnMe.spring.user.web.dto.UserRequestDTO;
import PerfumeOnMe.spring.user.web.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerDocs {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserResponseDTO.SignupResult>> signup(
		@RequestBody @Valid UserRequestDTO.Signup request) {
		UserResponseDTO.SignupResult result = userService.signup(request);
		return new ResponseEntity<>(ApiResponse.of(SuccessStatus._CREATED, result), HttpStatus.CREATED);
	}

	@PostMapping("/reissue")
	public ResponseEntity<ApiResponse<AuthResponseDTO.LoginResult>> reissue(
		@RequestHeader(name = "Refresh-Token") String refreshToken, HttpServletResponse response) {
		AuthResponseDTO.LoginResult result = userService.reissue(refreshToken, response);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(result));
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Object>> logout(HttpServletRequest request) {
		userService.logout(request);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
	}

	@DeleteMapping("/me")
	public ResponseEntity<ApiResponse<Object>> deleteUser(HttpServletRequest request) {
		userService.deleteUser(request);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
	}

	@PostMapping("/onboarding")
	public ResponseEntity<ApiResponse<Object>> onboarding(@Valid @RequestBody UserRequestDTO.Onboarding request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		userService.onboarding(request, userDetails);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
	}

	@PatchMapping("/me/notes")
	public ResponseEntity<ApiResponse<Object>> updateUserNote(@Valid @RequestBody UserRequestDTO.UserNoteUpdate request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		userService.updateUserNote(request, userDetails);
		return ResponseEntity.ok().body(ApiResponse.onSuccess(null));
	}

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserResponseDTO.MyPageProfileResponse>> getUserProfile(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		Long userId = userDetails.getUserId();
		UserResponseDTO.MyPageProfileResponse response = userService.getUserProfile(userId);
		return ResponseEntity.ok(ApiResponse.onSuccess(response));
	}

	@GetMapping("/favorites")
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceSearchFinalResult>> getFavoriteFragrances(
		@Valid @ModelAttribute FragranceRequestDTO.FragranceAllRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		Long userId = userDetails.getUserId();
		FragranceResponseDTO.FragranceSearchFinalResult favorites = userService.getFavoriteFragrances(request,
			userId);
		return ResponseEntity.ok(ApiResponse.onSuccess(favorites));
	}

	@PatchMapping("/me/image")
	public ResponseEntity<ApiResponse<Void>> updateProfileImage(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody @Valid UserRequestDTO.ProfileImageUpdateRequest request) {

		userService.updateProfileImage(userDetails.getUserId(), request.getImageUrl());
		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}
}
