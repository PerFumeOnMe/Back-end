package PerfumeOnMe.spring.fragrance.web.controller;

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
import PerfumeOnMe.spring.fragrance.service.FragranceService;
import PerfumeOnMe.spring.fragrance.web.docs.FragranceControllerDocs;
import PerfumeOnMe.spring.fragrance.web.dto.FragranceRequestDTO;
import PerfumeOnMe.spring.fragrance.web.dto.FragranceResponseDTO;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fragrances")
public class FragranceController implements FragranceControllerDocs {

	private final FragranceService fragranceService;

	/**
	 * 향수 상세 API
	 */
	@GetMapping("/allow/{fragranceId}")
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
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FavoriteResponseDTO>> addFavorite(
		@PathVariable("fragranceId") Long fragranceId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		FragranceResponseDTO.FavoriteResponseDTO result = fragranceService.addFavorite(userDetails.getUserId(),
			fragranceId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 향수 즐겨찾기 취소 API
	@DeleteMapping("/{fragranceId}/favorites")
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
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceMdChoiceResult>> getFragrancesMdChoice(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		FragranceResponseDTO.FragranceMdChoiceResult result = fragranceService.getFragranceMdChoice(userDetails);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	@GetMapping("/my-perfume")
	public ResponseEntity<ApiResponse<FragranceResponseDTO.FragranceMyPerfumeResult>> getFragrancesMyPerfume(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		FragranceResponseDTO.FragranceMyPerfumeResult result = fragranceService.getFragranceMyPerfume(userDetails);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}
