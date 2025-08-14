package PerfumeOnMe.spring.diary.web.docs;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.diary.web.dto.DiaryRequestDTO;
import PerfumeOnMe.spring.diary.web.dto.DiaryResponseDTO;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Diary", description = "다이어리 CRUD API")
public interface DiaryControllerDocs {

	@Operation(
		summary = "다이어리 추가",
		description = "다이어리를 추가하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "다이어리가 저장되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryResponseDTO.AddDiaryResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON401", description = "액세스 토큰을 입력해 주세요.")
		}
	)
	ResponseEntity<ApiResponse<DiaryResponseDTO.AddDiaryResponse>> addDiary(
		@RequestBody @Valid DiaryRequestDTO.AddDiaryRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "다이어리 수정",
		description = "다이어리를 수정하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "다이어리가 수정되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4001", description = "해당 다이어리를 찾을 수 없습니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4002", description = "다이어리 소유자의 요청이 아닙니다.")
		}
	)
	ResponseEntity<ApiResponse<Void>> updateDiary(
		@PathVariable Long diaryId,
		@RequestBody @Valid DiaryRequestDTO.UpdateDiaryRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "다이어리 삭제",
		description = "다이어리를 삭제하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "다이어리가 삭제되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4001", description = "해당 다이어리를 찾을 수 없습니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4002", description = "다이어리 소유자의 요청이 아닙니다.")
		}
	)
	ResponseEntity<ApiResponse<Void>> deleteDiary(
		@PathVariable Long diaryId,
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "일별 다이어리 상세 조회",
		description = "일별 다이어리를 상세 조회하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "다이어리가 조회되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryResponseDTO.SearchDailyDiaryResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4003", description = "해당 날짜에 해당하는 다이어리를 찾을 수 없습니다.")
		}
	)
	ResponseEntity<ApiResponse<List<DiaryResponseDTO.SearchDailyDiaryResponse>>> searchDailyDiary(
		@Parameter(
			description = "조회할 날짜 (예: 2025-07-17)"
		)
		@PathVariable LocalDate date,
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "월별 다이어리 조회",
		description = "월별 다이어리를 조회하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "다이어리가 조회되었습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryResponseDTO.SearchMonthlyDiaryResponse.class))),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "DIARY4004", description = "해당 월에 작성된 다이어리가 없습니다.")
		}
	)
	ResponseEntity<ApiResponse<List<DiaryResponseDTO.SearchMonthlyDiaryResponse>>> searchMonthlyDiary(
		@PathVariable Integer year,
		@PathVariable Integer month,
		@AuthenticationPrincipal CustomUserDetails userDetails);
}