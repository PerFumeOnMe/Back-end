package PerfumeOnMe.spring.fragrance.repository.fragranceLocation;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.fragrance.domain.mapping.FragranceLocation;

public interface FragranceLocationRepository
	extends JpaRepository<FragranceLocation, Long>, FragranceLocationRepositoryCustom {
}

