package PerfumeOnMe.spring.fragrance.web.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.fragrance.web.dto.FragranceRequestDTO;
import PerfumeOnMe.spring.fragrance.web.dto.FragranceResponseDTO;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Fragrance", description = "향수 CRUD API")
public interface FragranceControllerDocs {

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
	ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceDetailResult>> getFragranceDetail(
		@PathVariable("fragranceId") Long fragranceId,
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "향수 키워드 검색",
		description = "keyword 로 '향수이름' 또는 '브랜드'를 검색하고, 페이징 처리된 결과를 반환합니다.",
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
	ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceSearchFinalResult>> searchFragrances(
		@Valid @ModelAttribute FragranceRequestDTO.FragranceSearchRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails);

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
	ResponseEntity<ApiResponse<FragranceResponseDTO.FavoriteResponseDTO>> addFavorite(
		@PathVariable("fragranceId") Long fragranceId,
		@AuthenticationPrincipal CustomUserDetails userDetails);

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
	ResponseEntity<ApiResponse<FragranceResponseDTO.FavoriteCancelResponseDTO>> deleteFavorite(
		@PathVariable("fragranceId") Long fragranceId,
		@AuthenticationPrincipal CustomUserDetails userDetails);

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
	ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceSearchFinalResult>> searchFragrancesByFilter(
		@Valid @ModelAttribute FragranceRequestDTO.FragranceFilterRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "향수 전체 리스트 조회",
		description = "향수 전체 목록을 조회하는 API 입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceSearchResult.class))),
		}
	)
	ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceSearchFinalResult>> getFragrancesAll(
		FragranceRequestDTO.FragranceAllRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "메인페이지 추천 향수(MD's Choice) 목록 조회 API",
		description = "메인페이지에서 추천 향수(MD's Choice) 목록을 조회하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceMdChoiceResult.class))),
		}
	)
	ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceMdChoiceResult>> getFragrancesMdChoice(
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "메인페이지 나만의 향수 조회 API",
		description = "이미지키워드나 향수공방 중 가장 최근 결과에서 추천향수를 반환하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FragranceResponseDTO.FragranceMyPerfumeResult.class))),
		}
	)
	ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceMyPerfumeResult>> getFragrancesMyPerfume(
		@AuthenticationPrincipal CustomUserDetails userDetails);
}