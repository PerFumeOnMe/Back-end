package PerfumeOnMe.spring.web.dto.imagekeyword;

import PerfumeOnMe.spring.domain.enums.Ambience;
import PerfumeOnMe.spring.domain.enums.Gender;
import PerfumeOnMe.spring.domain.enums.Personality;
import PerfumeOnMe.spring.domain.enums.Season;
import PerfumeOnMe.spring.domain.enums.Style;
import PerfumeOnMe.spring.validation.annotation.ValidEnumKeyword;
import lombok.Getter;
import lombok.Setter;

/**
 * 이미지 키워드 관련 요청 DTO 모음 클래스
 */
public class ImageKeywordRequestDTO {
	@Getter
	@Setter
	public static class ImageKeywordPreviewRequestDTO {

		@ValidEnumKeyword(enumClass = Ambience.class)
		private String ambience;

		@ValidEnumKeyword(enumClass = Style.class)
		private String style;

		@ValidEnumKeyword(enumClass = Gender.class)
		private String gender;

		@ValidEnumKeyword(enumClass = Season.class)
		private String season;

		@ValidEnumKeyword(enumClass = Personality.class)
		private String personality;
	}
}