package PerfumeOnMe.spring.fragrance.repository.location;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.fragrance.domain.Location;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {
	Optional<Location> findByName(String name);
}