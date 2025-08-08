package PerfumeOnMe.spring.web.dto.workshop;

import java.util.Map;

import PerfumeOnMe.spring.validation.annotation.workshop.ValidBaseNote;
import PerfumeOnMe.spring.validation.annotation.workshop.ValidMiddleNote;
import PerfumeOnMe.spring.validation.annotation.workshop.ValidTopNote;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/** 향수공방 요청 DTO 클라스*/
@Schema(description = "향수공방 요청 DTO")
public class WorkshopRequestDTO {

	/** 향수공방 결과 생성 요청 DTO*/
	@Builder
	@Getter
	@Schema(description = "향수공방 결과 생성 요청 DTO")
	public static class WorkshopPreviewRequestDTO {

		@Schema(description = "탑 노트", example = "베르가못")
		@NotNull(message = "탑 노트 값은 필수 입니다")
		@ValidTopNote
		private String topNote;

		@Schema(description = "탑 노트 용량", example = "3")
		@NotNull(message = "탑 노트 용량 값은 필수 입니다")
		@Min(value = 0, message = "최소 탑 노트 용량은 0 이상 입니다.")
		@Max(value = 10, message = "최대 탑 노트 용량은 10 이하 입니다.")
		private Long topNoteVolume;

		@Schema(description = "미들 노트", example = "장미")
		@NotNull(message = "미들 노트 값은 필수 입니다")
		@ValidMiddleNote
		private String middleNote;

		@Schema(description = "미들 노트 용량", example = "3")
		@NotNull(message = "미들 노트 용량 값은 필수 입니다")
		@Min(value = 0, message = "최소 미들 노트 용량은 0 이상 입니다.")
		@Max(value = 10, message = "최대 미들 노트 용량은 10 이하 입니다.")
		private Long middleNoteVolume;

		@Schema(description = "베이스 노트", example = "바닐라")
		@NotNull(message = "베이스 노트 값은 필수 입니다")
		@ValidBaseNote
		private String baseNote;

		@Schema(description = "베이스 노트 용량", example = "4")
		@NotNull(message = "베이스 노트 용량 값은 필수 입니다")
		@Min(value = 0, message = "최소 베이스 노트 용량은 0 이상 입니다.")
		@Max(value = 10, message = "최대 베이스 노트 용량은 10 이하 입니다.")
		private Long baseNoteVolume;
	}

	/** 향수 추천을 위한 요청 DTO (내부적으로 사용) */
	@Builder
	@Getter
	@Schema(description = "향수 추천을 위한 요청 DTO")
	public static class WorkshopCreateRequestDTO {

		@Schema(description = "탑 노트 맵 (노트명: 용량)")
		private Map<String, Integer> topNoteList;

		@Schema(description = "미들 노트 맵 (노트명: 용량)")
		private Map<String, Integer> middleNoteList;

		@Schema(description = "베이스 노트 맵 (노트명: 용량)")
		private Map<String, Integer> baseNoteList;
	}

	/**향수공방 결과 저장을 위한 요청 DTO*/
	@Builder
	@Getter
	@Schema(description = "향수공방 결과 저장을 위한 DTO")
	public static class WorkshopSaveRequestDTO {
		@Schema(description = "결과를 저장할 이름", example = "나만의 겨울향기")
		@NotNull(message = "저장할 이름은 필수입니다")
		private String savedName;
	}
}
