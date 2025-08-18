package PerfumeOnMe.spring.fragrance.repository.price;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.fragrance.domain.Price;

public interface PriceRepository extends JpaRepository<Price, Long>, PriceRepositoryCustom {
	Optional<Price> findByMlCountAndPrice(Integer mlCount, Integer price);
}