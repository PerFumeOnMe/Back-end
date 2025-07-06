package PerfumeOnMe.spring.repository.fragrancePrice;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.domain.Price;
import PerfumeOnMe.spring.domain.mapping.FragrancePrice;

public interface FragrancePriceRepository extends JpaRepository<FragrancePrice, Long>, FragrancePriceRepositoryCustom {
	boolean existsByFragranceAndPrice(Fragrance fragrance, Price price);
}
