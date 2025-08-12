package PerfumeOnMe.spring.imagekeyword.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.imagekeyword.domain.ImageKeyword;
import PerfumeOnMe.spring.user.domain.User;

public interface ImageKeywordRepository extends JpaRepository<ImageKeyword, Long> {
	List<ImageKeyword> findAllByUserOrderByCreatedAtDesc(User user);

	Optional<ImageKeyword> findByIdAndUser(Long id, User user);

	// 동일한 사용자와 저장 이름이 존재하는지 확인
	boolean existsByUserAndSavedName(User user, String savedName);

	Optional<ImageKeyword> findFirstByUserOrderByCreatedAtDesc(User user);
}
