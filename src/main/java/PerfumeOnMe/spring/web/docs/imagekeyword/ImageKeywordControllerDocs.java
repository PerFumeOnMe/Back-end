package PerfumeOnMe.spring.web.docs.imagekeyword;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordRequestDTO;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Image-Keyword", description = "이미지키워드 API")
public interface ImageKeywordControllerDocs {

	@Operation(
		summary = "이미지 키워드 목록 조회 (마이페이지)",
		description = "해당 유저가 저장한 이미지 키워드 결과 목록을 조회합니다.",
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
	ResponseEntity<ApiResponse<List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO>>> getImageKeywordList(
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails
	);

	@Operation(
		summary = "이미지 키워드 상세 조회",
		description = "저장된 이미지 키워드 결과의 상세정보를 조회하는 API입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IK4004", description = "해당 ID의 이미지 키워드 결과를 찾을 수 없습니다.")
		}
	)
	ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO>> getImageKeywordDetail(
		@PathVariable Long imageKeywordId,
		@AuthenticationPrincipal CustomUserDetails userDetails
	);

	@Operation(
		summary = "이미지 키워드 결과 미리보기",
		description = "5가지 키워드를 기반으로 감성 시나리오 및 향수 추천 결과를 생성하여 미리 확인합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "잘못된 요청입니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON401", description = "인증이 필요합니다.")
		}
	)
	ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO>> getImageKeywordPreview(
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@Validated @RequestBody ImageKeywordRequestDTO.ImageKeywordPreviewRequestDTO request
	);

	@Operation(
		summary = "이미지 키워드 결과 저장",
		description = """
			프리뷰 확인 후 이름을 지정해 최종 저장합니다. 
			Redis에 임시 저장된 키워드를 기반으로 저장되며, 완료 후 해당 Redis 키는 삭제됩니다.
			""",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON200",
				description = "성공입니다.",
				content = @Content(schema = @Schema(implementation = ImageKeywordResponseDTO.ImageKeywordSaveResponseDTO.class))
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IK4002", description = "생성한 이미지 키워드 결과가 만료되었습니다."),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IK4003", description = "이미 동일한 이름으로 저장된 결과가 존재합니다.")
		}
	)
	ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordSaveResponseDTO>> saveImageKeywordResult(
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@Validated @RequestBody ImageKeywordRequestDTO.ImageKeywordSaveRequestDTO request
	);
}