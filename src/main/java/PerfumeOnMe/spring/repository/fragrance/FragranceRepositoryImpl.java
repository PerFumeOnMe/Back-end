package PerfumeOnMe.spring.repository.fragrance;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
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

	@Override
	public Page<Fragrance> findByKeyword(String keyword, Pageable pageable) {
		QFragrance fragrance = QFragrance.fragrance;

		// 실제 결과 데이터 조회
		List<Fragrance> content = queryFactory
			.selectFrom(fragrance)
			.where(containsKeyword(fragrance.name, keyword))
			.offset(pageable.getOffset()) // 몇 번째부터 가져올지 (page * size)
			.limit(pageable.getPageSize()) // 몇 개 가져올지
			.fetch();

		// 카운트 쿼리 -> 총 조회된 향수가 몇 개인지
		Long count = queryFactory
			.select(fragrance.count())
			.from(fragrance)
			.where(containsKeyword(fragrance.name, keyword))
			.fetchOne();

		// Page 객체 생성
		return PageableExecutionUtils.getPage(content, pageable, () -> count != null ? count : 0);
	}

	private BooleanExpression containsKeyword(StringPath field, String keyword) {
		return keyword == null ? null : field.containsIgnoreCase(keyword);
	}

}
