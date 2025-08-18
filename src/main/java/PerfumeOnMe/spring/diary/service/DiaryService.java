package PerfumeOnMe.spring.diary.service;

import java.time.LocalDate;
import java.util.List;

import PerfumeOnMe.spring.diary.web.dto.DiaryRequestDTO;
import PerfumeOnMe.spring.diary.web.dto.DiaryResponseDTO;

public interface DiaryService {

	// 다이어리 추가 API
	DiaryResponseDTO.AddDiaryResponse addDiary(Long userId, DiaryRequestDTO.AddDiaryRequest addDiaryRequest);

	// 다이어리 수정 API
	void updateDiary(Long userId, Long diaryId, DiaryRequestDTO.UpdateDiaryRequest updateDiaryRequest);

	// 다이어리 삭제 API
	void deleteDiary(Long userId, Long diaryId);

	// 일별 다이어리 상세 조회 API
	List<DiaryResponseDTO.SearchDailyDiaryResponse> searchDailyDiary(Long userId, LocalDate date);

	// 월별 다이어리 조회 API
	List<DiaryResponseDTO.SearchMonthlyDiaryResponse> searchMonthlyDiary(Long userId, LocalDate startDate,
		LocalDate endDate);
}
