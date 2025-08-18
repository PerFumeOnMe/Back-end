package PerfumeOnMe.spring.pbti.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.pbti.domain.PBTI;

public interface PbtiRepository extends JpaRepository<PBTI, Long>, PbtiRepositoryCustom {

	Optional<PBTI> findById(Long id);

	List<PBTI> findAllByUserId(Long userId);
}
