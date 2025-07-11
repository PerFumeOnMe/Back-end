package PerfumeOnMe.spring.service.Diary;

import PerfumeOnMe.spring.web.dto.diary.DiaryRequestDTO;
import PerfumeOnMe.spring.web.dto.diary.DiaryResponseDTO;

public interface DiaryService {

	// 다이어리 추가 API
	DiaryResponseDTO.AddDiaryResponse addDiary(Long userId, DiaryRequestDTO.AddDiaryRequest addDiaryRequest);
}
