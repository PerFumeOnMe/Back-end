package PerfumeOnMe.spring.web.dto.imagekeyword;

import PerfumeOnMe.spring.domain.enums.Ambience;
import PerfumeOnMe.spring.domain.enums.Gender;
import PerfumeOnMe.spring.domain.enums.Personality;
import PerfumeOnMe.spring.domain.enums.Season;
import PerfumeOnMe.spring.domain.enums.Style;
import PerfumeOnMe.spring.validation.annotation.ValidEnumKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 * 이미지 키워드 관련 요청 DTO 모음 클래스
 */
public class ImageKeywordRequestDTO {
	@Builder
	@Getter
	@Schema(description = "이미지 키워드 결과 생성 요청 DTO")
	public static class ImageKeywordPreviewRequestDTO {

		@ValidEnumKeyword(enumClass = Ambience.class)
		@NotNull(message = "이미지키워드 분위기 값은 필수입니다.")
		private String ambience;

		@ValidEnumKeyword(enumClass = Style.class)
		@NotNull(message = "이미지키워드 스타일 값은 필수입니다.")
		private String style;

		@ValidEnumKeyword(enumClass = Gender.class)
		@NotNull(message = "이미지키워드 성별 값은 필수입니다.")
		private String gender;

		@ValidEnumKeyword(enumClass = Season.class)
		@NotNull(message = "이미지키워드 계절 값은 필수입니다.")
		private String season;

		@ValidEnumKeyword(enumClass = Personality.class)
		@NotNull(message = "이미지키워드 성격 값은 필수입니다.")
		private String personality;
	}

	/**이미지 키워드 결과 저장을 위한 요청 DTO*/
	@Getter
	@Builder
	@Schema(description = "이미지 키워드 결과 저장을 위한 DTO")
	public static class ImageKeywordSaveRequestDTO {
		@Schema(description = "결과를 저장할 이름", example = "나만의 겨울향기 이미지키워드")
		@NotNull(message = "저장할 이름은 필수입니다")
		private String savedName;
	}
}