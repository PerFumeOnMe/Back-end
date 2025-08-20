package PerfumeOnMe.spring.external.fastapi.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FastApiRecommendResponse {
	private String scenario;
	private List<FragranceRecommendation> recommendations;

	@Getter
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
