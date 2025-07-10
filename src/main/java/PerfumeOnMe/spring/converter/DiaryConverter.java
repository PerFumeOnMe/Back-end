package PerfumeOnMe.spring.converter;

import PerfumeOnMe.spring.domain.mapping.Diary;
import PerfumeOnMe.spring.web.dto.diary.DiaryResponseDTO;

public class DiaryConverter {

	// 다이어리 추가 API
	public static DiaryResponseDTO.AddDiaryResponse addDiaryResponseDTO(Diary diary) {
		return DiaryResponseDTO.AddDiaryResponse.builder()
			.id(diary.getId())
			.date(diary.getDate())
			.build();
	}
}
