package PerfumeOnMe.spring.service.imagekeyword;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.domain.ImageKeywordDescription;
import PerfumeOnMe.spring.domain.enums.Ambience;
import PerfumeOnMe.spring.domain.enums.Gender;
import PerfumeOnMe.spring.domain.enums.KeywordCategory;
import PerfumeOnMe.spring.domain.enums.Personality;
import PerfumeOnMe.spring.domain.enums.Season;
import PerfumeOnMe.spring.domain.enums.Style;
import PerfumeOnMe.spring.repository.imagekeyworddescription.ImageKeywordDescriptionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageKeywordDescriptionService {

	private final ImageKeywordDescriptionRepository descriptionRepository;

	/**
	 * 키워드 + 카테고리 매핑 기반 설명 조합
	 * 순서대로 조회된 description들을 join하여 하나의 문장으로 반환
	 */
	public String getDescriptions(String ambience, String style, String gender, String season, String personality) {
		List<String> descriptions = Stream.of(
				new EnumWithCategory(Ambience.fromDisplayName(ambience).name(), KeywordCategory.AMBIENCE),
				new EnumWithCategory(Style.fromDisplayName(style).name(), KeywordCategory.STYLE),
				new EnumWithCategory(Season.fromDisplayName(season).name(), KeywordCategory.SEASON),
				new EnumWithCategory(Personality.fromDisplayName(personality).name(), KeywordCategory.PERSONALITY),
				new EnumWithCategory(Gender.fromDisplayName(gender).name(), KeywordCategory.GENDER)
			)
			.map(pair -> descriptionRepository
				.findByKeywordAndCategory(pair.keyword(), pair.category())
				.map(ImageKeywordDescription::getDescription)
				.orElse(""))
			.toList();

		return String.join(" ", descriptions);
	}

	private record EnumWithCategory(String keyword, KeywordCategory category) {
	}
}