package PerfumeOnMe.spring.repository.fragranceLocation;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.mapping.FragranceLocation;

public interface FragranceLocationRepository
	extends JpaRepository<FragranceLocation, Long>, FragranceLocationRepositoryCustom {
}

