package PerfumeOnMe.spring.web.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
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
@Tag(name = "Fragrance", description = "향수 CRUD API")
public class FragranceController {

	private final FragranceService fragranceService;

	// 향수 상세 API
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

	// 향수 검색 API
	@GetMapping("/search")
	@Operation(
		summary = "향수 키워드 검색 (무한 스크롤)",
		description = "keyword 로 향수 이름을 검색하고, 페이징 처리된 결과를 반환합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceSearchResult.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FRAGRANCE4002", description = "검색어를 2글자 이상 입력해주세요.")
		}
	)
	public ResponseEntity<ApiResponse<Map<String, Object>>> searchFragrances(
		@RequestParam String keyword, // 검색어
		@RequestParam int page, // 페이지 번호
		@RequestParam(defaultValue = "12") int size // 한 페이지에 불러올 향수 수 (default = 12)
	) {
		// 검색어가 공백이거나 2글자 미만이면 KEYWORD_TOO_SHORT 발생
		if (keyword == null || keyword.trim().length() < 2) {
			throw new GeneralException(ErrorStatus.KEYWORD_TOO_SHORT);
		}

		// result 안에 fragranceList 와 hasNext 를 키로 갖는 구조
		Map<String, Object> result = fragranceService.searchFragrances(keyword, page, size);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}
