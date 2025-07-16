package PerfumeOnMe.spring.web.dto.imagekeyword;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ImageKeywordResponseDTO {

	// 이미지키워드 목록 조회 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ImageKeywordListResponseDTO {
		private Long imageKeywordId;
		private String savedName;
		private LocalDateTime createdAt;
	}
}
