package PerfumeOnMe.spring.web.dto.Pbti;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class PbtiRequestDTO {

	// PBTI 결과 조회 요청 DTO
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class PbtiQuestionRequest {
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

	// PBTI 결과 저장 요청 DTO
	@Getter
	@Setter
	public static class PbtiSaveRequest {
		private String savedName;
	}

	// PBTI 결과 상세 조회 요청 DTO
	@Getter
	@Setter
	public static class PbtiResultDetailRequest {
		private Long pbtiId;
	}
}
