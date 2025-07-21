package PerfumeOnMe.spring.web.dto.Pbti;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PbtiResponseDTO {

	// PBTI 결과 조회 응답 DTO
	@Getter
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
		}
	}

	// Pbti 키워드 결과 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PbtiResult {
		private String keyword1; // J/P 기준
		private String keyword2; // S/N 기준
		private String keyword3; // T/F 기준
		private String keyword4; // E/I 기준
	}
}
