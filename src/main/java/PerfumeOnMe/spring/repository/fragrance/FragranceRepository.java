package PerfumeOnMe.spring.repository.fragrance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.Fragrance;

public interface FragranceRepository extends JpaRepository<Fragrance, Long>, FragranceRepositoryCustom {
	Optional<Fragrance> findByName(String name);

	Optional<Fragrance> findById(Long id);
}
