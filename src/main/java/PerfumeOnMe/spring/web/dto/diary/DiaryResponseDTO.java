package PerfumeOnMe.spring.web.dto.diary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import PerfumeOnMe.spring.domain.mapping.Diary;
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

	// 일별 다이어리 상세 조회 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SearchDailyDiaryResponse {
		private Long id;
		private String fragranceName;
		private LocalDate date;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public static List<SearchDailyDiaryResponse> fromEntityList(List<Diary> diaries) {
			return diaries.stream()
				.map(diary -> SearchDailyDiaryResponse.builder()
					.id(diary.getId())
					.fragranceName(diary.getFragranceName())
					.date(diary.getDate())
					.content(diary.getContent())
					.createdAt(diary.getCreatedAt())
					.updatedAt(diary.getUpdatedAt())
					.build())
				.toList();
		}
	}

}
