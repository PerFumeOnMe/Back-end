package PerfumeOnMe.spring.repository.fragrance;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.domain.QFragrance;
import PerfumeOnMe.spring.domain.QPrice;
import PerfumeOnMe.spring.domain.mapping.QFragrancePrice;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FragranceRepositoryImpl implements FragranceRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Fragrance> findByIdWithAllDetails(Long id) {
		QFragrance f = QFragrance.fragrance;
		QFragrancePrice fp = QFragrancePrice.fragrancePrice;
		QPrice price = QPrice.price1;

		Fragrance result = queryFactory.selectFrom(f)
			.leftJoin(f.fragrancePriceList, fp).fetchJoin()
			.leftJoin(fp.price, price).fetchJoin()
			.where(f.id.eq(id))
			.fetchOne();

		return Optional.ofNullable(result);
	}
}
