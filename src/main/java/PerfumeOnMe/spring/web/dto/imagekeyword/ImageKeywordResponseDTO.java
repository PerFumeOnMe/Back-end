package PerfumeOnMe.spring.web.dto.imagekeyword;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ImageKeywordResponseDTO {

	// 이미지키워드 목록 조회 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ImageKeywordListResponseDTO {
		private Long imageKeywordId;
		private String savedName;
		private LocalDateTime createdAt;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ImageKeywordDetailResponseDTO {
		private String savedName;
		private List<String> keywords;
		private String descriptions; // 설명들 join해서
		private String scenario;
		private String characterImageUrl;
		private List<FragranceRecommendation> recommendations;

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class FragranceRecommendation {
			private String brand;
			private String name;
			private String topNote;
			private String middleNote;
			private String baseNote;
			private String description;
			private List<String> relatedKeywords;
		}
	}
}
