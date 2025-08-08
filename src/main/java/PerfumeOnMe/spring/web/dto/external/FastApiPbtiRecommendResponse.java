package PerfumeOnMe.spring.web.dto.external;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FastApiPbtiRecommendResponse {
	private List<PbtiPerfumeRecommendation> perfumeRecommend = Collections.emptyList(); // 기본값

	public FastApiPbtiRecommendResponse(List<PbtiPerfumeRecommendation> perfumeRecommend) {
		this.perfumeRecommend = perfumeRecommend;
	}

	@Getter
	@NoArgsConstructor
	public static class PbtiPerfumeRecommendation {
		private String name;
		private String brand;
		private String description;
		private String perfumeImageUrl;
	}
}
