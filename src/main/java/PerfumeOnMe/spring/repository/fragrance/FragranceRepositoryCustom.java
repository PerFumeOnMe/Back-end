package PerfumeOnMe.spring.repository.fragrance;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceRequestDTO;

public interface FragranceRepositoryCustom {
	// 향수 상세
	Optional<Fragrance> findByIdWithAllDetails(Long id);

	// 향수 검색
	Page<Fragrance> findBySearchKeyword(String keyword, Pageable pageable);

	// 향수 필터링
	Page<Fragrance> findByFilter(FragranceRequestDTO.FragranceFilterRequest request, Pageable pageable);

}
