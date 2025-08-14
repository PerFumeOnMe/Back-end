package PerfumeOnMe.spring.uuid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.uuid.domain.Uuid;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
	Optional<Uuid> findByUuid(String uuid);
}
