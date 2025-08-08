package PerfumeOnMe.spring.web.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FastApiRecommendRequest {
	private String ambience;
	private String style;
	private String gender;
	private String season;
	private String personality;

	// PBTI용 요청 DTO
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PbtiRequest {
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
	}
}