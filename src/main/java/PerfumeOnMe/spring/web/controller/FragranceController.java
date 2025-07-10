package PerfumeOnMe.spring.web.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.service.fragrance.FragranceService;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceRequestDTO;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fragrances")
@Tag(name = "Fragrance", description = "향수 CRUD API")
public class FragranceController {

	private final FragranceService fragranceService;

	/**
	 * 향수 상세 API
	 */
	@GetMapping("/{fragranceId}")
	@Operation(
		summary = "향수 상세 조회",
		description = "향수 ID로 상세 정보를 조회하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceDetailResult.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FRAGRANCE4001", description = "해당 ID에 해당하는 향수를 찾을 수 없습니다.")
		}
	)
	@Parameters({
		@Parameter(name = "fragranceId", description = "향수 ID"),
	})
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceDetailResult>> getFragranceDetail(
		@PathVariable("fragranceId") Long fragranceId) {
		FragranceResponseDTO.FragranceDetailResult result = fragranceService.getFragranceDetail(fragranceId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	/**
	 * 향수 검색 API
	 */
	@GetMapping("/search")
	@Operation(
		summary = "향수 키워드 검색 (무한 스크롤)",
		description = "keyword 로 향수 이름을 검색하고, 페이징 처리된 결과를 반환합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceSearchResult.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FRAGRANCE4002", description = "검색어를 2글자 이상 입력해주세요.")
		}
	)
	@Parameters({
		@Parameter(name = "keyword", description = "검색어"),
		@Parameter(name = "page", description = "페이지 번호"),
		@Parameter(name = "size", description = "한 페이지에 불러올 향수 개수")
	})
	public ResponseEntity<ApiResponse<Map<String, Object>>> searchFragrances(
		@Valid @ModelAttribute FragranceRequestDTO.FragranceSearchRequest request
	) {
		// result 안에 fragranceList 와 hasNext 를 키로 갖는 구조
		Map<String, Object> result = fragranceService.searchFragrances(request.getKeyword(), request.getPage(),
			request.getSize());
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	/**
	 * 향수 필터링 API
	 */
	@GetMapping("/filter")
	@Operation(
		summary = "향수 필터링 검색 (무한 스크롤)",
		description = "필터링을 통해 걸러진 향수 목록을, 페이징 처리된 결과로 반환합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceSearchResult.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FILTER4001", description = "유효하지 않은 성별입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FILTER4002", description = "유효하지 않은 향수 타입입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FILTER4003", description = "유효하지 않은 노트 ID 입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FILTER4004", description = "유효하지 않은 계절 ID 입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FILTER4005", description = "유효하지 않은 장소 ID 입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FILTER4006", description = "가격 범위가 올바르지 않습니다.")
		}
	)
	@Parameters({
		@Parameter(name = "noteCategoryId", description = "향수 카테고리(노트) ID"),
		@Parameter(name = "fragranceType", description = "향수 타입 필터"),
		@Parameter(name = "gender", description = "성별 필터"),
		@Parameter(name = "situationId", description = "사용하는 상황 필터(Location ID)"),
		@Parameter(name = "seasonId", description = "계절 필터(계절 ID)"),
		@Parameter(name = "priceMin", description = "최소 가격"),
		@Parameter(name = "priceMax", description = "최대 가격"),
		@Parameter(name = "page", description = "페이지 번호 (0부터 시작)"),
		@Parameter(name = "size", description = "한 페이지에 불러올 향수 개수")
	})
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceSearchFinalResult>> searchFragrancesByFilter(
		@Valid @ModelAttribute FragranceRequestDTO.FragranceFilterRequest request
	) {
		FragranceResponseDTO.FragranceSearchFinalResult result = fragranceService.searchFragrancesByFilter(request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

}
