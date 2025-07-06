package PerfumeOnMe.spring.repository.season;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.Season;

public interface SeasonRepository extends JpaRepository<Season, Long>, SeasonRepositoryCustom {
	Optional<Season> findByName(String name);
}
