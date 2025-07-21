package PerfumeOnMe.spring.web.dto.imagekeyword;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 이미지 키워드 응답 DTO
public class ImageKeywordResponseDTO {

	// 이미지키워드 목록 조회 응답 DTO - 마이페이지 목록 조회용
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ImageKeywordListResponseDTO {
		private Long imageKeywordId;
		private String savedName;
		private LocalDateTime createdAt;
	}

	// 상세 조회용
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

	// Preview 응답용 (preview와 detail은 구조 동일 -> 저장 전 미리보기)
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ImageKeywordPreviewResponseDTO {
		private List<String> keywords;
		private String descriptions;
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

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ImageKeywordSaveResponseDTO {
		private Long imageKeywordId;
		private String savedName;
		private LocalDateTime createdAt;
	}

}