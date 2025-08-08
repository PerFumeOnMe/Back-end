package PerfumeOnMe.spring.repository.diary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.mapping.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {

	Optional<Diary> findById(Long id);

	List<Diary> findAllByUserIdAndDate(Long userId, LocalDate date);

	List<Diary> findAllByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
