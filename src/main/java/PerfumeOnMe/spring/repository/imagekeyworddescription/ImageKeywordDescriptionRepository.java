package PerfumeOnMe.spring.repository.imagekeyworddescription;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.ImageKeywordDescription;
import PerfumeOnMe.spring.domain.enums.KeywordCategory;

public interface ImageKeywordDescriptionRepository extends JpaRepository<ImageKeywordDescription, Long> {
	Optional<ImageKeywordDescription> findByKeywordAndCategory(String keyword, KeywordCategory category);

}
