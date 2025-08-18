package PerfumeOnMe.spring.imagekeyword.util;

import java.util.List;
import java.util.stream.Stream;

import PerfumeOnMe.spring.common.enums.Ambience;
import PerfumeOnMe.spring.common.enums.Gender;
import PerfumeOnMe.spring.common.enums.KeywordCategory;
import PerfumeOnMe.spring.common.enums.Personality;
import PerfumeOnMe.spring.common.enums.Season;
import PerfumeOnMe.spring.common.enums.Style;
import PerfumeOnMe.spring.imagekeyword.domain.ImageKeyword;
import PerfumeOnMe.spring.imagekeyword.domain.ImageKeywordDescription;
import PerfumeOnMe.spring.imagekeyword.repository.imagekeyworddescription.ImageKeywordDescriptionRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 이미지 키워드 설명 조회 공통 유틸리티
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageKeywordDescriptionUtils {

	/**
	 * 키워드 이름들을 기반으로 설명 조합
	 * @param ambience 분위기 키워드
	 * @param style 스타일 키워드
	 * @param gender 성별 키워드
	 * @param season 계절 키워드
	 * @param personality 성격 키워드
	 * @param descriptionRepository 설명 Repository
	 * @return 조합된 설명 문자열
	 */
	public static String getDescriptions(String ambience, String style, String gender, String season,
		String personality, ImageKeywordDescriptionRepository descriptionRepository) {

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
			.filter(desc -> !desc.isEmpty()) // 빈 문자열 필터링 추가
			.toList();

		return String.join(" ", descriptions);
	}

	/**
	 * ImageKeyword 엔티티로부터 설명 조합
	 * @param keyword ImageKeyword 엔티티
	 * @param descriptionRepository 설명 Repository
	 * @return 조합된 설명 문자열
	 */
	public static String getDescriptionsFromEntity(ImageKeyword keyword,
		ImageKeywordDescriptionRepository descriptionRepository) {

		List<String> descriptions = Stream.of(
				new EnumWithCategory(keyword.getAmbience().name(), KeywordCategory.AMBIENCE),
				new EnumWithCategory(keyword.getStyle().name(), KeywordCategory.STYLE),
				new EnumWithCategory(keyword.getSeason().name(), KeywordCategory.SEASON),
				new EnumWithCategory(keyword.getPersonality().name(), KeywordCategory.PERSONALITY),
				new EnumWithCategory(keyword.getGender().name(), KeywordCategory.GENDER)
			)
			.map(pair -> descriptionRepository.findByKeywordAndCategory(pair.keyword(), pair.category())
				.map(ImageKeywordDescription::getDescription)
				.orElse(""))
			.filter(desc -> !desc.isEmpty()) // 빈 문자열 필터링 추가
			.toList();

		return String.join(" ", descriptions);
	}

	/**
	 * 키워드와 카테고리를 묶는 내부 레코드
	 */
	private record EnumWithCategory(String keyword, KeywordCategory category) {
	}
}