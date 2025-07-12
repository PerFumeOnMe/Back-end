package PerfumeOnMe.spring.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.service.Diary.DiaryService;
import PerfumeOnMe.spring.web.dto.diary.DiaryRequestDTO;
import PerfumeOnMe.spring.web.dto.diary.DiaryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
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
}
