package PerfumeOnMe.spring.diary.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.apiPayload.code.status.SuccessStatus;
import PerfumeOnMe.spring.diary.service.DiaryService;
import PerfumeOnMe.spring.diary.web.docs.DiaryControllerDocs;
import PerfumeOnMe.spring.diary.web.dto.DiaryRequestDTO;
import PerfumeOnMe.spring.diary.web.dto.DiaryResponseDTO;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController implements DiaryControllerDocs {

	private final DiaryService diaryService;

	// 다이어리 추가 API
	@PostMapping("/write")
	public ResponseEntity<ApiResponse<DiaryResponseDTO.AddDiaryResponse>> addDiary(
		@RequestBody @Valid DiaryRequestDTO.AddDiaryRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		DiaryResponseDTO.AddDiaryResponse result = diaryService.addDiary(userDetails.getUserId(), request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 다이어리 수정 API
	@PatchMapping("/{diaryId}/update")
	public ResponseEntity<ApiResponse<Void>> updateDiary(
		@PathVariable Long diaryId,
		@RequestBody @Valid DiaryRequestDTO.UpdateDiaryRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		diaryService.updateDiary(userDetails.getUserId(), diaryId, request);
		return ResponseEntity.ok(ApiResponse.of(SuccessStatus.DIARY_UPDATED, null));
	}

	// 다이어리 삭제 API
	@DeleteMapping("/{diaryId}/delete")
	public ResponseEntity<ApiResponse<Void>> deleteDiary(
		@PathVariable Long diaryId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		diaryService.deleteDiary(userDetails.getUserId(), diaryId);
		return ResponseEntity.ok(ApiResponse.of(SuccessStatus.DIARY_DELETED, null));
	}

	// 일별 다이어리 상세 조회 API
	@GetMapping("/daily/{date}")
	public ResponseEntity<ApiResponse<List<DiaryResponseDTO.SearchDailyDiaryResponse>>> searchDailyDiary(
		@PathVariable LocalDate date,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		List<DiaryResponseDTO.SearchDailyDiaryResponse> result = diaryService.searchDailyDiary(userDetails.getUserId(),
			date);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 월별 다이어리 조회 API
	@GetMapping("/monthly/{year}/{month}")
	public ResponseEntity<ApiResponse<List<DiaryResponseDTO.SearchMonthlyDiaryResponse>>> searchMonthlyDiary(
		@PathVariable Integer year,
		@PathVariable Integer month,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

		List<DiaryResponseDTO.SearchMonthlyDiaryResponse> result =
			diaryService.searchMonthlyDiary(userDetails.getUserId(), startDate, endDate);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}
