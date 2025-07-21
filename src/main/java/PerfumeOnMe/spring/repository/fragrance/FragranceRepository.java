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
		LIMIT 6
		""", nativeQuery = true)
	List<Fragrance> findByUserMdChoice(@Param("gender") String gender,
		@Param("noteIdList") List<Long> userNoteIdList);
}
