package PerfumeOnMe.spring.fragrance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import PerfumeOnMe.spring.common.enums.FragranceGender;
import PerfumeOnMe.spring.common.enums.FragranceType;
import PerfumeOnMe.spring.fragrance.domain.Fragrance;
import PerfumeOnMe.spring.fragrance.domain.QFragrance;
import PerfumeOnMe.spring.fragrance.domain.QLocation;
import PerfumeOnMe.spring.fragrance.domain.QNote;
import PerfumeOnMe.spring.fragrance.domain.QPrice;
import PerfumeOnMe.spring.fragrance.domain.QSeason;
import PerfumeOnMe.spring.fragrance.domain.mapping.QFragranceBaseNote;
import PerfumeOnMe.spring.fragrance.domain.mapping.QFragranceLocation;
import PerfumeOnMe.spring.fragrance.domain.mapping.QFragranceMiddleNote;
import PerfumeOnMe.spring.fragrance.domain.mapping.QFragrancePrice;
import PerfumeOnMe.spring.fragrance.domain.mapping.QFragranceSeason;
import PerfumeOnMe.spring.fragrance.domain.mapping.QFragranceTopNote;
import PerfumeOnMe.spring.fragrance.web.dto.FragranceRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FragranceRepositoryImpl implements FragranceRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	// Q 도메인 객체를 클래스 레벨에서 선언
	private final QFragrance f = QFragrance.fragrance;
	private final QFragrancePrice fp = QFragrancePrice.fragrancePrice;
	private final QPrice price = QPrice.price1;
	private final QFragranceLocation fl = QFragranceLocation.fragranceLocation;
	private final QLocation l = QLocation.location;
	private final QFragranceSeason fs = QFragranceSeason.fragranceSeason;
	private final QSeason s = QSeason.season;
	private final QFragranceTopNote ftn = QFragranceTopNote.fragranceTopNote;
	private final QFragranceMiddleNote fmn = QFragranceMiddleNote.fragranceMiddleNote;
	private final QFragranceBaseNote fbn = QFragranceBaseNote.fragranceBaseNote;
	private final QNote topNote = new QNote("topNote");
	private final QNote middleNote = new QNote("middleNote");
	private final QNote baseNote = new QNote("baseNote");
	@PersistenceContext
	private EntityManager em;

	// 향수 상세
	@Override
	public Optional<Fragrance> findByIdWithAllDetails(Long id) {
		Fragrance result = queryFactory.selectFrom(f)
			.leftJoin(f.fragrancePriceList, fp).fetchJoin()
			.leftJoin(fp.price, price).fetchJoin()
			.where(f.id.eq(id))
			.fetchOne();

		return Optional.ofNullable(result);
	}

	//향수 검색
	@Override
	public Page<Fragrance> findBySearchKeyword(String keyword, Pageable pageable) {

		// 실제 결과 데이터 조회
		List<Fragrance> content = queryFactory
			.selectFrom(f)
			.where(containsKeyword(f.name, keyword))
			.offset(pageable.getOffset()) // 몇 번째부터 가져올지 (page * size)
			.limit(pageable.getPageSize()) // 몇 개 가져올지
			.fetch();

		// 카운트 쿼리 -> 총 조회된 향수가 몇 개인지
		Long count = queryFactory
			.select(f.count())
			.from(f)
			.where(containsKeyword(f.name, keyword))
			.fetchOne();

		// Page 객체 생성
		return PageableExecutionUtils.getPage(content, pageable, () -> count != null ? count : 0);
	}

	private BooleanExpression containsKeyword(StringPath field, String keyword) {
		return keyword == null ? null : field.containsIgnoreCase(keyword);
	}

	// 향수 필터링
	@Override
	public Page<Fragrance> findByFilter(FragranceRequestDTO.FragranceFilterRequest r, Pageable pageable) {

		BooleanBuilder whereClause = new BooleanBuilder();

		if (r.getFragranceType() != null) {
			whereClause.and(f.fragranceType.eq(FragranceType.valueOf(r.getFragranceType())));
		}
		if (r.getGender() != null) {
			whereClause.and(f.gender.eq(FragranceGender.valueOf(r.getGender())));
		}
		if (r.getSituationId() != null) {
			whereClause.and(l.id.eq(r.getSituationId()));
		}
		if (r.getSeasonId() != null) {
			whereClause.and(s.id.eq(r.getSeasonId()));
		}
		if (r.getNoteCategoryId() != null) {
			BooleanBuilder noteBuilder = new BooleanBuilder();
			noteBuilder.or(topNote.top.isTrue().and(topNote.id.eq(r.getNoteCategoryId())));
			noteBuilder.or(middleNote.middle.isTrue().and(middleNote.id.eq(r.getNoteCategoryId())));
			noteBuilder.or(baseNote.base.isTrue().and(baseNote.id.eq(r.getNoteCategoryId())));
			whereClause.and(noteBuilder);
		}
		if (r.getPriceMin() != null && r.getPriceMax() != null) {
			whereClause.and(price.price.between(r.getPriceMin(), r.getPriceMax()));
		} else if (r.getPriceMin() != null) {
			whereClause.and(price.price.goe(r.getPriceMin()));
		} else if (r.getPriceMax() != null) {
			whereClause.and(price.price.loe(r.getPriceMax()));
		}

		List<Fragrance> result = queryFactory.selectFrom(f)
			.distinct()
			.leftJoin(f.fragrancePriceList, fp).fetchJoin()
			.leftJoin(fp.price, price)
			.leftJoin(f.fragranceLocationList, fl).leftJoin(fl.location, l)
			.leftJoin(f.fragranceSeasonList, fs).leftJoin(fs.season, s)
			.leftJoin(f.fragranceTopNoteList, ftn).leftJoin(ftn.note, topNote)
			.leftJoin(f.fragranceMiddleNoteList, fmn).leftJoin(fmn.note, middleNote)
			.leftJoin(f.fragranceBaseNoteList, fbn).leftJoin(fbn.note, baseNote)
			.where(whereClause)
			.orderBy(f.name.asc()) // "가,나,다 순으로 정렬"
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory.select(f.countDistinct())
			.from(f)
			.leftJoin(f.fragrancePriceList, fp)
			.leftJoin(fp.price, price)
			.leftJoin(f.fragranceLocationList, fl).leftJoin(fl.location, l)
			.leftJoin(f.fragranceSeasonList, fs).leftJoin(fs.season, s)
			.leftJoin(f.fragranceTopNoteList, ftn).leftJoin(ftn.note, topNote)
			.leftJoin(f.fragranceMiddleNoteList, fmn).leftJoin(fmn.note, middleNote)
			.leftJoin(f.fragranceBaseNoteList, fbn).leftJoin(fbn.note, baseNote)
			.where(whereClause)
			.fetchOne();

		return new PageImpl<>(result, pageable, total != null ? total : 0);
	}
}
