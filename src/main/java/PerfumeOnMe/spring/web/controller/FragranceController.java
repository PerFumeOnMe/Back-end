package PerfumeOnMe.spring.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.service.fragrance.FragranceService;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fragrances")
@Tag(name = "Fragrance", description = "향수 조회 API")
public class FragranceController {

	private final FragranceService fragranceService;

	@GetMapping("/{fragranceId}")
	@Operation(
		summary = "향수 상세 조회",
		description = "향수 ID로 상세 정보를 조회하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceDetailResult.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FRAGRANCE4001", description = "해당 ID에 해당하는 향수를 찾을 수 없습니다.")
		}
	)
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceDetailResult>> getFragranceDetail(
		@PathVariable("fragranceId") Long fragranceId) {
		FragranceResponseDTO.FragranceDetailResult result = fragranceService.getFragranceDetail(fragranceId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}
