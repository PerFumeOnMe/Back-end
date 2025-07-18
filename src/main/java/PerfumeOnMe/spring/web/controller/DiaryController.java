package PerfumeOnMe.spring.web.controller;

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
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.service.Diary.DiaryService;
import PerfumeOnMe.spring.web.dto.diary.DiaryRequestDTO;
import PerfumeOnMe.spring.web.dto.diary.DiaryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
@Tag(name = "Diary", description = "다이어리 CRUD API")
public class DiaryController {

	private final DiaryService diaryService;

	// 다이어리 추가 API
	@PostMapping("/write")
	@Operation(
		summary = "다이어리 추가",
		description = "다이어리를 추가하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "다이어리가 저장되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryResponseDTO.AddDiaryResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON401", description = "액세스 토큰을 입력해 주세요.")
		}
	)
	public ResponseEntity<ApiResponse<DiaryResponseDTO.AddDiaryResponse>> addDiary(
		@RequestBody @Valid DiaryRequestDTO.AddDiaryRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		DiaryResponseDTO.AddDiaryResponse result = diaryService.addDiary(userDetails.getUserId(), request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 다이어리 수정 API
	@PatchMapping("/{diaryId}/update")
	@Operation(
		summary = "다이어리 수정",
		description = "다이어리를 수정하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "다이어리가 수정되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4001", description = "해당 다이어리를 찾을 수 없습니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4002", description = "다이어리 소유자의 요청이 아닙니다.")
		}
	)
	public ResponseEntity<ApiResponse<Void>> updateDiary(
		@PathVariable Long diaryId,
		@RequestBody @Valid DiaryRequestDTO.UpdateDiaryRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		diaryService.updateDiary(userDetails.getUserId(), diaryId, request);
		return ResponseEntity.ok(ApiResponse.of(SuccessStatus.DIARY_UPDATED, null));
	}

	// 다이어리 삭제 API
	@DeleteMapping("/{diaryId}/delete")
	@Operation(
		summary = "다이어리 삭제",
		description = "다이어리를 삭제하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "다이어리가 삭제되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4001", description = "해당 다이어리를 찾을 수 없습니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4002", description = "다이어리 소유자의 요청이 아닙니다.")
		}
	)
	public ResponseEntity<ApiResponse<Void>> deleteDiary(
		@PathVariable Long diaryId,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		diaryService.deleteDiary(userDetails.getUserId(), diaryId);
		return ResponseEntity.ok(ApiResponse.of(SuccessStatus.DIARY_DELETED, null));
	}

	// 일별 다이어리 상세 조회 API
	@GetMapping("/daily/{date}")
	@Operation(
		summary = "일별 다이어리 상세 조회",
		description = "일별 다이어리를 상세 조회하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "다이어리가 조회되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryResponseDTO.SearchDailyDiaryResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4003", description = "해당 날짜에 해당하는 다이어리를 찾을 수 없습니다.")
		}
	)
	public ResponseEntity<ApiResponse<List<DiaryResponseDTO.SearchDailyDiaryResponse>>> searchDailyDiary(
		@Parameter(
			description = "조회할 날짜 (예: 2025-07-17)"
		)
		@PathVariable LocalDate date,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		List<DiaryResponseDTO.SearchDailyDiaryResponse> result = diaryService.searchDailyDiary(userDetails.getUserId(),
			date);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}
