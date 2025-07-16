package PerfumeOnMe.spring.repository.imagekeyword;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.ImageKeyword;
import PerfumeOnMe.spring.domain.User;

public interface ImageKeywordRepository extends JpaRepository<ImageKeyword, Long> {
	List<ImageKeyword> findAllByUserOrderByCreatedAtDesc(User user);
}
