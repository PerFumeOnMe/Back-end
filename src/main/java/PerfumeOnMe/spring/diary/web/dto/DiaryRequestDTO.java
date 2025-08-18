package PerfumeOnMe.spring.diary.web.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

public class DiaryRequestDTO {

	// 다이어리 추가 요청 DTO
	@Getter
	@Setter
	public static class AddDiaryRequest {
		private String fragranceName;
		private String content;
		private LocalDate date;
	}

	// 다이어리 수정 요청 DTO
	@Getter
	@Setter
	public static class UpdateDiaryRequest {
		private String fragranceName;
		private String content;
	}

}
