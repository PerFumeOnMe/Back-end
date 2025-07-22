package PerfumeOnMe.spring.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.service.pbti.PbtiService;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiRequestDTO;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pbti")
@Tag(name = "PBTI", description = "PBTI CRUD API")
public class PbtiController {

	private final PbtiService pbtiService;

	// PBTI 결과 조회 API
	@PostMapping("/result")
	@Operation(
		summary = "PBTI 결과 조회",
		description = "8개의 질문 선택지를 기반으로 PBTI 분석 결과를 조회합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "PBTI 결과가 조회되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PbtiResponseDTO.PbtiQuestionResponse.class)))
		}
	)
	public ResponseEntity<ApiResponse<PbtiResponseDTO.PbtiQuestionResponse>> searchPbti(
		@RequestBody PbtiRequestDTO.PbtiQuestionRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		PbtiResponseDTO.PbtiQuestionResponse result = pbtiService.searchPbti(userDetails.getUserId(), request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}
