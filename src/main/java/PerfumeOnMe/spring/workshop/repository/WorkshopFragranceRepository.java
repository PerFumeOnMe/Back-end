package PerfumeOnMe.spring.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import PerfumeOnMe.spring.workshop.domain.WorkshopFragrance;

public interface WorkshopFragranceRepository extends JpaRepository<WorkshopFragrance, Long> {

	// 노트로 향수 검색 (탑, 미들, 베이스 노트에서 키워드 포함)
	@Query("SELECT wf FROM WorkshopFragrance wf WHERE " +
		"wf.topNote LIKE %:note% OR " +
		"wf.middleNote LIKE %:note% OR " +
		"wf.baseNote LIKE %:note%")
	List<WorkshopFragrance> findByNoteContaining(@Param("note") String note);

	// 메인어코드로 향수 검색 (1,2,3순위에서 매칭)
	@Query("SELECT wf FROM WorkshopFragrance wf WHERE " +
		"wf.mainAccord1 = :accord OR " +
		"wf.mainAccord2 = :accord OR " +
		"wf.mainAccord3 = :accord")
	List<WorkshopFragrance> findByMainAccordContaining(@Param("accord") String accord);

	// 특정 노트들이 포함된 향수 검색 (복수 노트)
	@Query("SELECT wf FROM WorkshopFragrance wf WHERE " +
		"(:topNote IS NULL OR wf.topNote LIKE %:topNote%) AND " +
		"(:middleNote IS NULL OR wf.middleNote LIKE %:middleNote%) AND " +
		"(:baseNote IS NULL OR wf.baseNote LIKE %:baseNote%)")
	List<WorkshopFragrance> findByNotesContaining(
		@Param("topNote") String topNote,
		@Param("middleNote") String middleNote,
		@Param("baseNote") String baseNote);

	// 가격 범위로 향수 검색
	@Query("SELECT wf FROM WorkshopFragrance wf WHERE wf.price BETWEEN :minPrice AND :maxPrice")
	List<WorkshopFragrance> findByPriceBetween(@Param("minPrice") Integer minPrice,
		@Param("maxPrice") Integer maxPrice);

	// 모든 향수 조회 (추천 알고리즘용)
	@Query("SELECT wf FROM WorkshopFragrance wf ORDER BY wf.id")
	List<WorkshopFragrance> findAllForRecommendation();
}