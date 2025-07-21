package PerfumeOnMe.spring.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.service.imagekeyword.ImageKeywordPreviewService;
import PerfumeOnMe.spring.service.imagekeyword.ImageKeywordService;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordRequestDTO;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image-keyword")
@Tag(name = "Image-Keyword", description = "이미지키워드 API")
public class ImageKeywordController {
	private final ImageKeywordService imageKeywordService;
	private final ImageKeywordPreviewService previewService;

	// 이미지키워드 목록 조회 API
	@GetMapping("/result/list")
	@Operation(
		summary = "이미지 키워드 목록 조회 (마이페이지)",
		description = "해당 유저가 저장한 이미지 키워드 결과 목록을 조회합니다.\n\n" +
			"마이페이지 내 '추천 결과' 영역에 출력되는 카드들의 리스트 데이터입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON200",
				description = "요청에 성공하였습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ImageKeywordResponseDTO.ImageKeywordListResponseDTO.class)
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON401",
				description = "인증이 필요합니다. 액세스 토큰을 입력해주세요."
			)
		}
	)
	public ResponseEntity<ApiResponse<List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO>>> getImageKeywordList(
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO> result = imageKeywordService.getImageKeywordList(
			userDetails.getUserId());
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 이미지 키워드 결과 상세조회
	@GetMapping("/{imageKeywordId}")
	@Operation(
		summary = "이미지 키워드 상세 조회",
		description = "저장된 이미지 키워드 결과의 상세정보를 조회하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IK4004", description = "해당 ID의 이미지 키워드 결과를 찾을 수 없습니다.")
		}
	)
	public ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO>> getImageKeywordDetail(
		@PathVariable Long imageKeywordId,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO result =
			imageKeywordService.getImageKeywordDetail(userDetails.getUserId(), imageKeywordId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	// 이미지 키워드 결과 미리보기 (preview)
	@PostMapping("/preview")
	@Operation(
		summary = "이미지 키워드 결과 미리보기",
		description = "5가지 키워드를 기반으로 감성 시나리오 및 향수 추천 결과를 생성하여 미리 확인합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON401", description = "인증이 필요합니다.")
		}
	)
	public ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO>> getImageKeywordPreview(
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@Validated @RequestBody ImageKeywordRequestDTO.ImageKeywordPreviewRequestDTO request
	) {
		Long userId = userDetails.getUserId();
		ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO result = previewService.generatePreview(userId, request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}