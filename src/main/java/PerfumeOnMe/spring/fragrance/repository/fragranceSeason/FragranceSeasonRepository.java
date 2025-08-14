package PerfumeOnMe.spring.fragrance.repository.fragranceSeason;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.fragrance.domain.mapping.FragranceSeason;

public interface FragranceSeasonRepository
	extends JpaRepository<FragranceSeason, Long>, FragranceSeasonRepositoryCustom {
}
