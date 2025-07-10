package PerfumeOnMe.spring.service.fragrance;

import java.util.Map;

import PerfumeOnMe.spring.web.dto.fragrance.FragranceRequestDTO;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceResponseDTO;

public interface FragranceService {
	// 향수 상세 API
	FragranceResponseDTO.FragranceDetailResult getFragranceDetail(Long fragranceId);

	// 향수 검색 API
	Map<String, Object> searchFragrances(String keyword, int page, int size);

	// 향수 즐겨찾기 등록 API
	FragranceResponseDTO.FavoriteResponseDTO addFavorite(Long userId, Long fragranceId);

	// 향수 필터링 API
	FragranceResponseDTO.FragranceSearchFinalResult searchFragrancesByFilter(
		FragranceRequestDTO.FragranceFilterRequest request);
}

