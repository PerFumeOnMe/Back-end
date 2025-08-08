package PerfumeOnMe.spring.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "PBTI 결과가 조회되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PbtiResponseDTO.PbtiQuestionResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PBTI4002", description = "GPT 응답 Json 파싱 과정에서 에러가 발생했습니다.")
		}
	)
	public ResponseEntity<ApiResponse<PbtiResponseDTO.PbtiQuestionResponse>> searchPbti(
		@RequestBody PbtiRequestDTO.PbtiQuestionRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		PbtiResponseDTO.PbtiQuestionResponse result = pbtiService.searchPbti(userDetails.getUserId(), request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// PBTI 결과 저장 API
	@PostMapping("/save")
	@Operation(
		summary = "PBTI 결과 저장",
		description = "PBTI 분석 결과를 DB에 저장합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "PBTI 결과가 저장되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PbtiResponseDTO.PbtiSaveResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PBTI4004", description = "사용자의 PBTI 분석 결과가 Redis에서 만료되었거나 저장되어 있지 않습니다.")
		}
	)
	public ResponseEntity<ApiResponse<PbtiResponseDTO.PbtiSaveResponse>> savePbti(
		@RequestBody PbtiRequestDTO.PbtiSaveRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		PbtiResponseDTO.PbtiSaveResponse result = pbtiService.savePbti(userDetails.getUserId(), request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 마이페이지 PBTI 목록 조회 API
	@GetMapping("/result/list")
	@Operation(
		summary = "마이페이지 PBTI 목록 조회",
		description = "마이페이지에서 PBTI 목록을 조회합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "PBTI 목록이 조회되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PbtiResponseDTO.PbtiListResult.class)))
		}
	)
	public ResponseEntity<ApiResponse<PbtiResponseDTO.SearchPbtiListResponse>> searchPbtiList(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		PbtiResponseDTO.SearchPbtiListResponse result = pbtiService.searchPbtiList(userDetails.getUserId());
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 마이페이지 PBTI 결과 상세 조회 API
	@PostMapping("/detailResult")
	@Operation(
		summary = "마이페이지 PBTI 결과 상세 조회",
		description = "마이페이지에서 PBTI 결과를 상세 조회합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "PBTI 결과가 상세 조회되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PbtiResponseDTO.PbtiResultDetailResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PBTI4005", description = "존재하지 않는 PBTI 입니다.")
		}
	)
	public ResponseEntity<ApiResponse<PbtiResponseDTO.PbtiResultDetailResponse>> searchPbtiResult(
		@RequestBody PbtiRequestDTO.PbtiResultDetailRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		PbtiResponseDTO.PbtiResultDetailResponse result = pbtiService.searchPbtiResult(userDetails.getUserId(),
			request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// PBTI 결과 이름 수정 API
	@PatchMapping("/{pbtiId}/name")
	@Operation(
		summary = "PBTI 결과 이름 수정",
		description = "pbtiId에 해당하는 PBTI 결과 이름을 수정합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "PBTI 결과 이름이 수정되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PbtiResponseDTO.UpdatePbtiNameResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PBTI4005", description = "존재하지 않는 PBTI 입니다.")
		}
	)
	public ResponseEntity<ApiResponse<PbtiResponseDTO.UpdatePbtiNameResponse>> updatePbtiName(
		@PathVariable Long pbtiId,
		@RequestBody PbtiRequestDTO.UpdatePbtiNameRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		PbtiResponseDTO.UpdatePbtiNameResponse result = pbtiService.updatePbtiName(userDetails.getUserId(),
			pbtiId, request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// PBTI 결과 삭제 API
	@DeleteMapping("/{pbtiId}/result")
	@Operation(
		summary = "PBTI 결과 삭제",
		description = "pbtiId에 해당하는 PBTI 결과를 삭제합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "PBTI 결과가 삭제되었습니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PBTI4005", description = "존재하지 않는 PBTI 입니다.")
		}
	)
	public ResponseEntity<ApiResponse<Void>> deletePbtiResult(
		@PathVariable Long pbtiId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		Void result = pbtiService.deletePbtiResult(userDetails.getUserId(), pbtiId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}
