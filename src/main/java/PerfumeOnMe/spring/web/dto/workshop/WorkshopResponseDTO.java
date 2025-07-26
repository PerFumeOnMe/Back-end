package PerfumeOnMe.spring.web.dto.workshop;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "향수공방 응답 DTO")
public class WorkshopResponseDTO {

	@Builder
	@Getter
	@Schema(description = "마이페이지 향수공방 목록 응답")
	public static class WorkshopListResponseDTO {
		private Long workshopId;
		private String savedName;
		private LocalDateTime createdAt;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(description = "향수공방 결과 상세조회")
	public static class WorkshopDetailResponseDTO {

		@Schema(description = "키워드 요약", example = "#상큼한첫인상 #감정전달향 #무디한마무리")
		private String keywordSummary;

		@Schema(description = "첫인상 설명", example = "상큼한 시트러스가 먼저 퍼지며, 활기차고 개방적인 에너지를 전달합니다...")
		private String firstImpression;

		@Schema(description = "중간 인상 설명", example = "곧이어 재스민의 은은한 꽃향기가 중심을 잡습니다...")
		private String centerImpression;

		@Schema(description = "마지막 인상 설명", example = "이 향기의 핵심은 단연 우디 노트입니다...")
		private String lastImpression;

		@Schema(description = "성향 분석", example = "🌞 겉으로는 밝고 유쾌하며 누구든 쉽게 다가갈 수 있는 사람...")
		private String tendency;

		@Schema(description = "추천 향수 목록")
		@JsonProperty("recommendedFragranceJson")
		private List<RecommendedFragranceDTO> recommendedFragranceJson;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(description = "추천 향수 정보")
	public static class RecommendedFragranceDTO {

		@Schema(description = "브랜드명", example = "딥디크")
		private String brand;

		@Schema(description = "향수명", example = "탐다오")
		private String name;

		@Schema(description = "향수 설명", example = "백단향의 고요한 잔향이 매력적인 향수")
		private String description;

		@Schema(description = "향수 가격", example = "30000")
		private int price;

		@Schema(description = "이미지 URL", example = "www.s3.com")
		private String imageUrl;
	}

}
