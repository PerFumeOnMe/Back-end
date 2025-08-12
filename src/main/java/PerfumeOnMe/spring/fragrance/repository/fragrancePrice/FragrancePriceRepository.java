package PerfumeOnMe.spring.fragrance.repository.fragrancePrice;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.fragrance.domain.Fragrance;
import PerfumeOnMe.spring.fragrance.domain.Price;
import PerfumeOnMe.spring.fragrance.domain.mapping.FragrancePrice;

public interface FragrancePriceRepository extends JpaRepository<FragrancePrice, Long>, FragrancePriceRepositoryCustom {
	boolean existsByFragranceAndPrice(Fragrance fragrance, Price price);
}
