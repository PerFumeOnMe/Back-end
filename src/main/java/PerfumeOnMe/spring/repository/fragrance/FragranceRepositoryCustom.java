package PerfumeOnMe.spring.repository.fragrance;

import java.util.Optional;

import PerfumeOnMe.spring.domain.Fragrance;

public interface FragranceRepositoryCustom {
	Optional<Fragrance> findByIdWithAllDetails(Long id);
}
