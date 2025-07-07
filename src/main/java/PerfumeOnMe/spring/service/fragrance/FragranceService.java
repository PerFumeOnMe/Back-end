package PerfumeOnMe.spring.service.fragrance;

import PerfumeOnMe.spring.web.dto.fragrance.FragranceResponseDTO;

public interface FragranceService {
	FragranceResponseDTO.FragranceDetailResult getFragranceDetail(Long fragranceId);
}

