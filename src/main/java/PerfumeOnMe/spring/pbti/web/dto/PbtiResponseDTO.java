package PerfumeOnMe.spring.pbti.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PbtiResponseDTO {

	// PBTI 결과 조회 응답 DTO
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PbtiQuestionResponse {
		private String recommendation;
		private List<Keyword> keywords;
		private PerfumeStyle perfumeStyle;
		private List<ScentPoint> scentPoint;
		private String summary;
		private List<PerfumeRecommend> perfumeRecommend;

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class Keyword {
			private String keyword;
			private String keywordDescription;
		}

		@Getter
		@Setter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class PerfumeStyle {
			private String description;
			private List<Note> notes;

			@Getter
			@Builder
			@AllArgsConstructor
			@NoArgsConstructor
			public static class Note {
				private String category;
				private String categoryDescription;
			}
		}

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class ScentPoint {
			private String category;
			private int point;
		}

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class PerfumeRecommend {
			private String name;
			private String brand;
			private String description;
			private String perfumeImageUrl;
		}
	}

	// Pbti 키워드 결과 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PbtiResult {
		private String keyword1;
		private String keyword2;
		private String keyword3;
		private String keyword4;
	}

	// Pbti 결과 저장 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PbtiSaveResponse {
		private Long id;
		private String savedName;
		private LocalDateTime createdAt;
	}

	// Redis 저장용 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PbtiRedisDTO {
		@JsonProperty("qOne")
		private String qOne;

		@JsonProperty("qTwo")
		private String qTwo;

		@JsonProperty("qThree")
		private String qThree;

		@JsonProperty("qFour")
		private String qFour;

		@JsonProperty("qFive")
		private String qFive;

		@JsonProperty("qSix")
		private String qSix;

		@JsonProperty("qSeven")
		private String qSeven;

		@JsonProperty("qEight")
		private String qEight;

		private String recommendation;
		private String summary;

		private List<PbtiQuestionResponse.Keyword> keywords;
		private PbtiQuestionResponse.PerfumeStyle perfumeStyle;
		private List<PbtiQuestionResponse.ScentPoint> scentPoint;
		private List<PbtiQuestionResponse.PerfumeRecommend> perfumeRecommend;
	}

	// 마이페이지 PBTI 목록 조회 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PbtiListResult {
		private Long id;
		private String savedName;
		private LocalDateTime createdAt;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SearchPbtiListResponse {
		private List<PbtiListResult> result;
	}

	// PBTI 결과 상세 조회 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PbtiResultDetailResponse {
		private String savedName;
		private String recommendation;
		private List<Keyword> keywords;
		private PerfumeStyle perfumeStyle;
		private List<ScentPoint> scentPoint;
		private String summary;
		private List<PerfumeRecommend> perfumeRecommend;

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class Keyword {
			private String keyword;
			private String keywordDescription;
		}

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class PerfumeStyle {
			private String description;
			private List<Note> notes;

			@Getter
			@Builder
			@AllArgsConstructor
			@NoArgsConstructor
			public static class Note {
				private String category;
				private String categoryDescription;
			}
		}

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class ScentPoint {
			private String category;
			private int point;
		}

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class PerfumeRecommend {
			private String name;
			private String brand;
			private String description;
			private String perfumeImageUrl;
		}
	}

	// PBTI 결과 이름 수정 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UpdatePbtiNameResponse {
		private Long id;
		private String savedName;
	}

}
