package PerfumeOnMe.spring.repository.pbti;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.PBTI;

public interface PbtiRepository extends JpaRepository<PBTI, Long>, PbtiRepositoryCustom {

	Optional<PBTI> findById(Long id);
}
