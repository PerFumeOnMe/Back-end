package PerfumeOnMe.spring.fragrance.service;

import PerfumeOnMe.spring.fragrance.web.dto.FragranceRequestDTO;
import PerfumeOnMe.spring.fragrance.web.dto.FragranceResponseDTO;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;

public interface FragranceService {
	// 향수 상세 API
	FragranceResponseDTO.FragranceDetailResult getFragranceDetail(Long fragranceId, Long userId);

	// 향수 검색 API
	FragranceResponseDTO.FragranceSearchFinalResult searchFragrances(FragranceRequestDTO.FragranceSearchRequest request,
		Long userId);

	// 향수 즐겨찾기 등록 API
	FragranceResponseDTO.FavoriteResponseDTO addFavorite(Long userId, Long fragranceId);

	// 향수 즐겨찾기 취소 API
	FragranceResponseDTO.FavoriteCancelResponseDTO deleteFavorite(Long userId, Long fragranceId);

	// 향수 필터링 API
	FragranceResponseDTO.FragranceSearchFinalResult searchFragrancesByFilter(
		FragranceRequestDTO.FragranceFilterRequest request, Long userId);

	// 향수 전체 리스트 조회 API
	FragranceResponseDTO.FragranceSearchFinalResult getFragranceListAll(FragranceRequestDTO.FragranceAllRequest request,
		Long userId);

	FragranceResponseDTO.FragranceMdChoiceResult getFragranceMdChoice(CustomUserDetails userDetails);

	// 향수 메인페이지 나만의 향수 조회 API
	FragranceResponseDTO.FragranceMyPerfumeResult getFragranceMyPerfume(CustomUserDetails userDetails);
}

