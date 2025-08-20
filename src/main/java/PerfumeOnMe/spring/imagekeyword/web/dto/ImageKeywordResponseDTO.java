package PerfumeOnMe.spring.imagekeyword.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 이미지 키워드 응답 DTO
@Schema(description = "이미지 키워드 응답 DTO")
public class ImageKeywordResponseDTO {

	// 이미지키워드 목록 조회 응답 DTO - 마이페이지 목록 조회용
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(description = "이미지 키워드 향수공방 목록 응답")
	public static class ImageKeywordListResponseDTO {
		@Schema(description = "이미지 키워드 아이디", example = "1")
		private Long imageKeywordId;

		@Schema(description = "저장된 이미지 키워드 이름", example = "이미지 키워드 해봤는데 맘에드는거1")
		private String savedName;

		@Schema(description = "생성날짜")
		private LocalDateTime createdAt;
	}

	/**이미지 키워드 결과 상세조회*/
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(description = "이미지 키워드 결과 상세조회")
	public static class ImageKeywordDetailResponseDTO {
		@Schema(description = "저장된 이미지 키워드 이름", example = "이미지 키워드 해봤는데 맘에드는거1")
		private String savedName;

		@Schema(description = "선택한 이미지 키워드", example = "세련된,유니크한,겨울,조용한,여성적인")
		private List<String> keywords;

		@Schema(description = "이미지키워드 설명 나열", example = "세련됨은 어쩌구를 의미해요. ... 여성적인은 저쩌구를 의미해요.")
		private String descriptions; // 설명들 join해서

		@Schema(description = "감성 시나리오", example = "잔잔한 눈이 내리고, 따뜻한 햇살이 얼굴을 감싸오는 장면에에요.")
		private String scenario;

		@Schema(description = "감성 캐릭터", example = "www.imagecharacter.com")
		private String characterImageUrl;

		@Schema(description = "추천 향수")
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
			private String imageUrl;
			private String removebgImageUrl;
		}
	}

	// Preview 응답용
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
			private String imageUrl;
			private String removebgImageUrl;
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