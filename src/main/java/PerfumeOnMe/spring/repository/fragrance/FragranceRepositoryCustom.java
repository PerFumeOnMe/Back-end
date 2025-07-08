package PerfumeOnMe.spring.repository.fragrance;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import PerfumeOnMe.spring.domain.Fragrance;

public interface FragranceRepositoryCustom {
	Optional<Fragrance> findByIdWithAllDetails(Long id);

	Page<Fragrance> findByKeyword(String keyword, Pageable pageable);
}
