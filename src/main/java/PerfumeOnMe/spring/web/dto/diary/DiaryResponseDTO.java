package PerfumeOnMe.spring.web.dto.diary;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DiaryResponseDTO {

	// 다이어리 추가 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class AddDiaryResponse {
		private Long id;
		private LocalDate date;
	}

}
