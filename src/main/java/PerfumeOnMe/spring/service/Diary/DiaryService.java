package PerfumeOnMe.spring.service.Diary;

import java.time.LocalDate;
import java.util.List;

import PerfumeOnMe.spring.web.dto.diary.DiaryRequestDTO;
import PerfumeOnMe.spring.web.dto.diary.DiaryResponseDTO;

public interface DiaryService {

	// 다이어리 추가 API
	DiaryResponseDTO.AddDiaryResponse addDiary(Long userId, DiaryRequestDTO.AddDiaryRequest addDiaryRequest);

	// 다이어리 수정 API
	void updateDiary(Long userId, Long diaryId, DiaryRequestDTO.UpdateDiaryRequest updateDiaryRequest);

	// 다이어리 삭제 API
	void deleteDiary(Long userId, Long diaryId);

	// 일별 다이어리 상세 조회 API
	List<DiaryResponseDTO.SearchDailyDiaryResponse> searchDailyDiary(Long userId, LocalDate date);
}
