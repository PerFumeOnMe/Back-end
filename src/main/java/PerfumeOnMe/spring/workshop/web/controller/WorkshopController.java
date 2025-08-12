package PerfumeOnMe.spring.workshop.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.workshop.service.WorkshopService;
import PerfumeOnMe.spring.workshop.web.docs.WorkshopControllerDocs;
import PerfumeOnMe.spring.workshop.web.dto.WorkshopRequestDTO;
import PerfumeOnMe.spring.workshop.web.dto.WorkshopResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workshop")
public class WorkshopController implements WorkshopControllerDocs {

	private final WorkshopService workshopService;

	/** 향수공방 결과 미리보기(결과 생성)*/
	@PostMapping("/preview")
	public ResponseEntity<ApiResponse<WorkshopResponseDTO.WorkshopPreviewResponseDTO>> getWorkshopPreview(
		@RequestBody @Valid WorkshopRequestDTO.WorkshopPreviewRequestDTO request,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(ApiResponse.onSuccess(workshopService.
			createWorkshopPreview(request, userDetails)));
	}

	/** 향수공방 결과 저장 */
	@PostMapping("/save")
	public ResponseEntity<ApiResponse<WorkshopResponseDTO.WorkshopSaveResponseDTO>> saveWorkshopResult(
		@RequestBody @Valid WorkshopRequestDTO.WorkshopSaveRequestDTO request,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(ApiResponse.onSuccess(workshopService.
			saveWorkshop(request, userDetails)));
	}

	/** 향수공방 목록 조회*/
	@GetMapping("/result/list")
	public ResponseEntity<ApiResponse<List<WorkshopResponseDTO.WorkshopListResponseDTO>>> getWorkshopList(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(ApiResponse.onSuccess(workshopService
			.findAllWorkshopsByUser(
				userDetails)));
	}

	/** 향수공방 결과 상세조회*/
	@GetMapping("/{workshopId}")
	public ResponseEntity<ApiResponse<WorkshopResponseDTO.WorkshopDetailResponseDTO>> getWorkshopDetail(
		@PathVariable Long workshopId,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(ApiResponse.onSuccess(workshopService
			.findWorkshopById(workshopId,
				userDetails)));
	}

}
