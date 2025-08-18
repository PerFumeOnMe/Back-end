package PerfumeOnMe.spring.pbti.web.controller;

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
import PerfumeOnMe.spring.pbti.service.PbtiService;
import PerfumeOnMe.spring.pbti.web.docs.PbtiControllerDocs;
import PerfumeOnMe.spring.pbti.web.dto.PbtiRequestDTO;
import PerfumeOnMe.spring.pbti.web.dto.PbtiResponseDTO;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pbti")
public class PbtiController implements PbtiControllerDocs {

	private final PbtiService pbtiService;

	// PBTI 결과 조회 API
	@PostMapping("/result")
	public ResponseEntity<ApiResponse<PbtiResponseDTO.PbtiQuestionResponse>> searchPbti(
		@RequestBody PbtiRequestDTO.PbtiQuestionRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		PbtiResponseDTO.PbtiQuestionResponse result = pbtiService.searchPbti(userDetails.getUserId(), request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// PBTI 결과 저장 API
	@PostMapping("/save")
	public ResponseEntity<ApiResponse<PbtiResponseDTO.PbtiSaveResponse>> savePbti(
		@RequestBody PbtiRequestDTO.PbtiSaveRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		PbtiResponseDTO.PbtiSaveResponse result = pbtiService.savePbti(userDetails.getUserId(), request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 마이페이지 PBTI 목록 조회 API
	@GetMapping("/result/list")
	public ResponseEntity<ApiResponse<PbtiResponseDTO.SearchPbtiListResponse>> searchPbtiList(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		PbtiResponseDTO.SearchPbtiListResponse result = pbtiService.searchPbtiList(userDetails.getUserId());
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 마이페이지 PBTI 결과 상세 조회 API
	@PostMapping("/detailResult")
	public ResponseEntity<ApiResponse<PbtiResponseDTO.PbtiResultDetailResponse>> searchPbtiResult(
		@RequestBody PbtiRequestDTO.PbtiResultDetailRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		PbtiResponseDTO.PbtiResultDetailResponse result = pbtiService.searchPbtiResult(userDetails.getUserId(),
			request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// PBTI 결과 이름 수정 API
	@PatchMapping("/{pbtiId}/name")
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
	public ResponseEntity<ApiResponse<Void>> deletePbtiResult(
		@PathVariable Long pbtiId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		Void result = pbtiService.deletePbtiResult(userDetails.getUserId(), pbtiId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}
