package PerfumeOnMe.spring.diary.converter;

import PerfumeOnMe.spring.diary.web.dto.DiaryResponseDTO;
import PerfumeOnMe.spring.diary.domain.Diary;

public class DiaryConverter {

	// 다이어리 추가 API
	public static DiaryResponseDTO.AddDiaryResponse addDiaryResponseDTO(Diary diary) {
		return DiaryResponseDTO.AddDiaryResponse.builder()
			.id(diary.getId())
			.date(diary.getDate())
			.build();
	}
}
