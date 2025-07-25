package PerfumeOnMe.spring.repository.fragrance;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import PerfumeOnMe.spring.domain.Fragrance;

public interface FragranceRepository extends JpaRepository<Fragrance, Long>, FragranceRepositoryCustom {
	Optional<Fragrance> findByName(String name);

	Optional<Fragrance> findById(Long id);

	/**
	 * 메인페이지 향수 추천(MD's Choice) - 사용자 성별과 선호 향으로 추천
	 * 1. 향수 탑, 미들, 베이스 노트 아이디 합집합
	 * 2. DISTINCT로 노트 아이디 중복 제거
	 * 3. 사용자 선호 향과 일치하는 개수 카운트
	 * 4. 개수 기반 우선순위 설정 후 정렬
	 * 5. 6개 반환
	 * @param gender = 사용자 성별; null로 들어온 경우 WHERE 절에서 무시
	 * @param userNoteIdList = 사용자 선호 향
	 * @return = 향수 6개
	 */
	@Query(value = """
		SELECT f.*
		FROM fragrances f
		LEFT JOIN (
			SELECT fn.fragrance_id, COUNT(DISTINCT fn.note_id) AS match_cnt
			FROM (
				SELECT fragrance_id, note_id FROM fragrance_top_notes
				UNION
				SELECT fragrance_id, note_id FROM fragrance_middle_notes
				UNION
				SELECT fragrance_id, note_id FROM fragrance_base_notes
			) AS fn
			WHERE fn.note_id IN (:noteIdList)
			GROUP BY fn.fragrance_id
		) AS match_note ON match_note.fragrance_id = f.id
		WHERE :gender IS NULL OR f.gender = :gender
		ORDER BY
			CASE
				WHEN COALESCE(match_note.match_cnt, 0) = 3 THEN 1
				WHEN COALESCE(match_note.match_cnt, 0) = 2 THEN 2
				WHEN COALESCE(match_note.match_cnt, 0) = 1 THEN 3
				ELSE 4
			END ASC
		LIMIT 9
		""", nativeQuery = true)
	List<Fragrance> findByUserMdChoice(@Param("gender") String gender,
		@Param("noteIdList") List<Long> userNoteIdList);
}
