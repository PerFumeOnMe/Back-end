package PerfumeOnMe.spring.imagekeyword.web.controller;

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
import PerfumeOnMe.spring.imagekeyword.service.ImageKeywordPreviewService;
import PerfumeOnMe.spring.imagekeyword.service.ImageKeywordService;
import PerfumeOnMe.spring.imagekeyword.web.docs.ImageKeywordControllerDocs;
import PerfumeOnMe.spring.imagekeyword.web.dto.ImageKeywordRequestDTO;
import PerfumeOnMe.spring.imagekeyword.web.dto.ImageKeywordResponseDTO;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image-keyword")
public class ImageKeywordController implements ImageKeywordControllerDocs {
	private final ImageKeywordService imageKeywordService;
	private final ImageKeywordPreviewService previewService;

	/**(1) 이미지 키워드 결과 미리보기 (preview)*/
	@PostMapping("/preview")
	public ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO>> getImageKeywordPreview(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@Validated @RequestBody ImageKeywordRequestDTO.ImageKeywordPreviewRequestDTO request
	) {
		Long userId = userDetails.getUserId();
		ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO result =
			previewService.generatePreview(userId, request);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	/**(2) 이미지 키워드 결과 저장 (save)*/
	@PostMapping("/save")
	public ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordSaveResponseDTO>> saveImageKeywordResult(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@Validated @RequestBody ImageKeywordRequestDTO.ImageKeywordSaveRequestDTO request
	) {
		ImageKeywordResponseDTO.ImageKeywordSaveResponseDTO result =
			imageKeywordService.saveImageKeyword(userDetails.getUserId(), request.getSavedName());
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	/**(3) 이미지 키워드 결과 상세조회*/
	@GetMapping("/{imageKeywordId}")
	public ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO>> getImageKeywordDetail(
		@PathVariable Long imageKeywordId,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO result =
			imageKeywordService.getImageKeywordDetail(userDetails.getUserId(), imageKeywordId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	/**(4) 이미지 키워드 목록 조회 API*/
	@GetMapping("/result/list")
	public ResponseEntity<ApiResponse<List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO>>> getImageKeywordList(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO> result =
			imageKeywordService.getImageKeywordList(
				userDetails.getUserId());
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

}