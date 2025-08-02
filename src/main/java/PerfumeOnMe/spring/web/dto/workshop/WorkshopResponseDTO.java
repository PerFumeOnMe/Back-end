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

		@Schema(description = "향수공방 아이디", example = "1")
		private Long workshopId;

		@Schema(description = "저장된 향수공방 이름", example = "향수공방 해봤는데 맘에드는거1")
		private String savedName;

		@Schema(description = "생성날짜")
		private LocalDateTime createdAt;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(description = "향수공방 결과 상세조회")
	public static class WorkshopDetailResponseDTO {

		@Schema(description = "시각적 키워드 (해시태그 형태)", example = "#상큼한첫인상 #감성적중심 #우디잔향\n#깊이있는사람 #신뢰감있는향기")
		private String keywordSummary;

		@Schema(description = "향기의 첫인상 (탑 노트 설명 + 사용자 성향)", example = "베르가못의 상쾌한 시트러스 향이 첫 만남을 장식합니다. 이런 향을 선택하는 당신은 활기차고 긍정적인 에너지를 가진 사람으로 보입니다.")
		private String firstImpression;

		@Schema(description = "중심을 잡는 향 (미들 노트 설명 + 사용자 성향)", example = "장미의 우아한 플로럴 향이 중심을 잡으며 로맨틱함을 연출합니다. 이런 향을 좋아하는 당신은 섬세하고 감성적인 면을 가진 사람입니다.")
		private String centerImpression;

		@Schema(description = "마지막에 남는 잔향 (베이스 노트 설명 + 사용자 성향)", example = "샌달우드의 깊고 따뜻한 잔향이 오래도록 머물며 안정감을 줍니다. 이런 향을 선택하는 당신은 차분하고 신뢰할 수 있는 성격의 소유자입니다.")
		private String lastImpression;

		@Schema(description = "향기로 해석한 당신의 성향 (전체 분석)", example = "복합적이고 다층적인 매력을 가진 당신은 첫인상은 밝고 활기차지만, 깊이 알수록 더욱 매력적인 면을 발견하게 됩니다. 당신은 사람들에게 '기분 좋은 여운이 오래 남는 사람'으로 기억됩니다.")
		private String tendency;

		@Schema(description = "향기로 해석한 당신의 성향 (기억되는 모습)", example = "당신은 사람들에게 '기분 좋은 여운이 오래 남는 사람'으로 기억됩니다.")
		private String remembered;

		@Schema(description = "추천 향수 목록")
		@JsonProperty("recommendedFragranceJson")
		private List<RecommendedFragranceDTO> recommendedFragranceJson;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(description = "향수공방 결과 미리보기 생성")
	public static class WorkshopPreviewResponseDTO {

		@Schema(description = "탑 노트", example = "베르가못")
		private String topNote;

		@Schema(description = "탑 노트 용량", example = "3")
		private Long topNoteVolume;

		@Schema(description = "미들 노트", example = "장미")
		private String middleNote;

		@Schema(description = "미들 노트 용량", example = "4")
		private Long middleNoteVolume;

		@Schema(description = "베이스 노트", example = "바닐라")
		private String baseNote;

		@Schema(description = "베이스 노트 용량", example = "3")
		private Long baseNoteVolume;

		@Schema(description = "시각적 키워드 (해시태그 형태)", example = "#상큼한첫인상 #감성적중심 #우디잔향\n#깊이있는사람 #신뢰감있는향기")
		private String keywordSummary;

		@Schema(description = "향기의 첫인상 (탑 노트 설명 + 사용자 성향)", example = "베르가못의 상쾌한 시트러스 향이 첫 만남을 장식합니다. 이런 향을 선택하는 당신은 활기차고 긍정적인 에너지를 가진 사람으로 보입니다.")
		private String firstImpression;

		@Schema(description = "중심을 잡는 향 (미들 노트 설명 + 사용자 성향)", example = "장미의 우아한 플로럴 향이 중심을 잡으며 로맨틱함을 연출합니다. 이런 향을 좋아하는 당신은 섬세하고 감성적인 면을 가진 사람입니다.")
		private String centerImpression;

		@Schema(description = "마지막에 남는 잔향 (베이스 노트 설명 + 사용자 성향)", example = "샌달우드의 깊고 따뜻한 잔향이 오래도록 머물며 안정감을 줍니다. 이런 향을 선택하는 당신은 차분하고 신뢰할 수 있는 성격의 소유자입니다.")
		private String lastImpression;

		@Schema(description = "향기로 해석한 당신의 성향 (전체 분석)", example = "복합적이고 다층적인 매력을 가진 당신은 첫인상은 밝고 활기차지만, 깊이 알수록 더욱 매력적인 면을 발견하게 됩니다. 당신은 사람들에게 '기분 좋은 여운이 오래 남는 사람'으로 기억됩니다.")
		private String tendency;

		@Schema(description = "향기로 해석한 당신의 성향 (기억되는 모습)", example = "당신은 사람들에게 '기분 좋은 여운이 오래 남는 사람'으로 기억됩니다.")
		private String remembered;

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

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(description = "향수공방 저장 결과")
	public static class WorkshopSaveResponseDTO {
		@Schema(description = "향수공방 아이디", example = "34")
		private Long workshopId;

		@Schema(description = "향수공방 이름", example = "나만의 시나몬 겨울항기")
		private String savedName;

		@Schema(description = "생성 날짜")
		private LocalDateTime createdAt;
	}

}
