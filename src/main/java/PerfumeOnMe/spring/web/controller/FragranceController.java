package PerfumeOnMe.spring.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
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
	@GetMapping("/allow/{fragranceId}")
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
		@PathVariable("fragranceId") Long fragranceId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		Long userId = (userDetails != null) ? userDetails.getUserId() : null;
		FragranceResponseDTO.FragranceDetailResult result = fragranceService.getFragranceDetail(fragranceId,
			userId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	/**
	 * 향수 검색 API
	 */
	@GetMapping("/allow/search")
	@Operation(
		summary = "향수 키워드 검색",
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
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceSearchFinalResult>> searchFragrances(
		@Valid @ModelAttribute FragranceRequestDTO.FragranceSearchRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long userId = (userDetails != null) ? userDetails.getUserId() : null;
		FragranceResponseDTO.FragranceSearchFinalResult result = fragranceService.searchFragrances(request, userId);

		return ResponseEntity.ok(ApiResponse.onSuccess(result));

	}

	// 향수 즐겨찾기 등록 API
	@PostMapping("/{fragranceId}/favorites")
	@Operation(
		summary = "향수 즐겨찾기 등록",
		description = "향수 ID로 향수 즐겨찾기를 등록하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "향수를 즐겨찾기에 등록했습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FavoriteResponseDTO.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAVORITES4001", description = "이미 즐겨찾기에 등록한 향수입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FRAGRANCE4001", description = "해당 ID에 해당하는 향수를 찾을 수 없습니다.")
		}
	)
	@Parameters({
		@Parameter(name = "fragranceId", description = "향수 ID"),
	})
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FavoriteResponseDTO>> addFavorite(
		@PathVariable("fragranceId") Long fragranceId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		FragranceResponseDTO.FavoriteResponseDTO result = fragranceService.addFavorite(userDetails.getUserId(),
			fragranceId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 향수 즐겨찾기 취소 API
	@DeleteMapping("/{fragranceId}/favorites")
	@Operation(
		summary = "향수 즐겨찾기 취소",
		description = "향수 ID로 향수 즐겨찾기를 취소하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "즐겨찾기에서 향수를 제거했습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FavoriteCancelResponseDTO.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FAVORITES4002", description = "즐겨찾기 목록에 존재하지 않는 향수입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FRAGRANCE4001", description = "해당 ID에 해당하는 향수를 찾을 수 없습니다.")
		}
	)
	@Parameters({
		@Parameter(name = "fragranceId", description = "향수 ID"),
	})
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FavoriteCancelResponseDTO>> deleteFavorite(
		@PathVariable("fragranceId") Long fragranceId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		FragranceResponseDTO.FavoriteCancelResponseDTO result = fragranceService.deleteFavorite(userDetails.getUserId(),
			fragranceId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	/**
	 * 향수 필터링 API
	 */
	@GetMapping("/allow/filter")
	@Operation(
		summary = "향수 필터링 검색 ",
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
		@Valid @ModelAttribute FragranceRequestDTO.FragranceFilterRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long userId = (userDetails != null) ? userDetails.getUserId() : null;
		FragranceResponseDTO.FragranceSearchFinalResult result = fragranceService.searchFragrancesByFilter(request,
			userId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	/**
	 * 향수 전체 리스트 API
	 */

	@GetMapping("/allow/all")
	@Operation(
		summary = "향수 전체 리스트 조회",
		description = "향수 전체 목록을 조회하는 API 입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceSearchResult.class))),
		}
	)
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceSearchFinalResult>> getFragrancesAll(
		FragranceRequestDTO.FragranceAllRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long userId = (userDetails != null) ? userDetails.getUserId() : null;
		FragranceResponseDTO.FragranceSearchFinalResult result = fragranceService.getFragranceListAll(request,
			userId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	@GetMapping("/md-choice")
	@Operation(
		summary = "메인페이지 추천 향수(MD's Choice) 목록 조회 API",
		description = "메인페이지에서 추천 향수(MD's Choice) 목록을 조회하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceMdChoiceResult.class))),
		}
	)
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceMdChoiceResult>> getFragrancesMdChoice(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		FragranceResponseDTO.FragranceMdChoiceResult result = fragranceService.getFragranceMdChoice(userDetails);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	@GetMapping("/my-perfume")
	@Operation(
		summary = "메인페이지 나만의 향수 조회 API",
		description = "이미지키워드나 향수공방 중 가장 최근 결과에서 추천향수를 반환하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceMyPerfumeResult.class))),
		}
	)
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceMyPerfumeResult>> getFragrancesMyPerfume(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		FragranceResponseDTO.FragranceMyPerfumeResult result = fragranceService.getFragranceMyPerfume(userDetails);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}
