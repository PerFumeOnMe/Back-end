package PerfumeOnMe.spring.web.dto.workshop;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

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

}
