package PerfumeOnMe.spring.imagekeyword.repository.imagekeyworddescription;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.common.enums.KeywordCategory;
import PerfumeOnMe.spring.imagekeyword.domain.ImageKeywordDescription;

public interface ImageKeywordDescriptionRepository extends JpaRepository<ImageKeywordDescription, Long> {
	Optional<ImageKeywordDescription> findByKeywordAndCategory(String keyword, KeywordCategory category);

}
