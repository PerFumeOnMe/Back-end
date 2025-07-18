package PerfumeOnMe.spring.service.Diary;

import PerfumeOnMe.spring.web.dto.diary.DiaryRequestDTO;
import PerfumeOnMe.spring.web.dto.diary.DiaryResponseDTO;

public interface DiaryService {

	// 다이어리 추가 API
	DiaryResponseDTO.AddDiaryResponse addDiary(Long userId, DiaryRequestDTO.AddDiaryRequest addDiaryRequest);

	// 다이어리 수정 API
	void updateDiary(Long userId, Long diaryId, DiaryRequestDTO.UpdateDiaryRequest updateDiaryRequest);

	// 다이어리 삭제 API
	void deleteDiary(Long userId, Long diaryId);
}
