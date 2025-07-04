package PerfumeOnMe.spring.repository.fragranceSeason;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.mapping.FragranceSeason;

public interface FragranceSeasonRepository
	extends JpaRepository<FragranceSeason, Long>, FragranceSeasonRepositoryCustom {
}
