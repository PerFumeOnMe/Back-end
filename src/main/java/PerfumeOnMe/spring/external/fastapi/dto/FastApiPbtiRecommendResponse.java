package PerfumeOnMe.spring.external.fastapi.dto;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FastApiPbtiRecommendResponse {
	private String recommendation;
	private List<Keyword> keywords;
	private PerfumeStyle perfumeStyle;
	private List<ScentPoint> scentPoint;
	private String summary;
	private List<PbtiPerfumeRecommendation> perfumeRecommend = Collections.emptyList(); // 기본값

	public FastApiPbtiRecommendResponse(List<PbtiPerfumeRecommendation> perfumeRecommend) {
		this.perfumeRecommend = perfumeRecommend;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Keyword {
		private String keyword;
		private String keywordDescription;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class PerfumeStyle {
		private String description;
		private List<Note> notes;

		@Getter
		@Setter
		@NoArgsConstructor
		@AllArgsConstructor
		@Builder
		public static class Note {
			private String category;
			private String categoryDescription;
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ScentPoint {
		private String category;
		private int point;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class PbtiPerfumeRecommendation {
		private String name;
		private String brand;
		private String description;
		private String perfumeImageUrl;
	}
}
