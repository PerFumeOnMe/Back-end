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
				description = "인증이 필요합니다. 액세스 토큰을 입력해주세요.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(example = """
						{
						  "isSuccess": false,
						  "code": "COMMON401",
						  "message": "인증이 필요합니다."
						}
						""")
				)
			)
		}
	)
	ResponseEntity<ApiResponse<List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO>>> getImageKeywordList(
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails
	);

	@Operation(
		summary = "이미지 키워드 상세 조회",
		description = "저장된 이미지 키워드 결과의 상세정보를 조회합니다. 키워드 요약, 시나리오, 추천 향수 목록을 포함합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON200",
				description = "요청에 성공하였습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO.class)
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON401",
				description = "인증이 필요합니다. 액세스 토큰을 입력해주세요.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(example = """
						{
						  "isSuccess": false,
						  "code": "COMMON401",
						  "message": "인증이 필요합니다."
						}
						""")
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "IMAGEKEYWORD4003",
				description = "해당 이미지 키워드 결과 정보가 존재하지 않습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(example = """
						{
						  "isSuccess": false,
						  "code": "IMAGEKEYWORD4003",
						  "message": "해당 이미지 키워드 결과 정보가 존재하지 않습니다."
						}
						""")
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "IMAGEKEYWORD4004",
				description = "해당 이미지 키워드 결과에 접근할 수 없습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(example = """
						{
						  "isSuccess": false,
						  "code": "IMAGEKEYWORD4004",
						  "message": "해당 이미지 키워드 결과에 접근할 수 없습니다."
						  }
						""")
				)
			)
		}
	)
	ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO>> getImageKeywordDetail(
		@Parameter(description = "조회할 이미지 키워드 결과 ID", required = true, example = "1")
		@PathVariable Long imageKeywordId,
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails
	);

	@Operation(
		summary = "이미지 키워드 결과 미리보기",
		description = "사용자가 선택한 5가지 키워드(분위기, 스타일, 성별, 계절, 성격)를 바탕으로 감성 시나리오 및 향수 추천 결과를 생성하여 미리 확인합니다. " +
			"결과는 Redis에 15분간 임시 저장되며, 이미지 키워드 저장 API 호출 시 활용됩니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON200",
				description = "요청에 성공하였습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO.class)
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON400",
				description = "잘못된 요청입니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(example = """
						{
						  "isSuccess": false,
						  "code": "COMMON400",
						  "message": "잘못된 요청입니다."
						}
						""")
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON401",
				description = "인증이 필요합니다. 액세스 토큰을 입력해주세요.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(example = """
						{
						  "isSuccess": false,
						  "code": "COMMON401",
						  "message": "인증이 필요합니다."
						}
						""")
				)
			)
		}
	)
	ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO>> getImageKeywordPreview(
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@Parameter(description = "이미지 키워드 미리보기 생성 요청", required = true)
		@Validated @RequestBody ImageKeywordRequestDTO.ImageKeywordPreviewRequestDTO request
	);

	@Operation(
		summary = "이미지 키워드 결과 저장",
		description = "사용자가 미리보기에서 확인한 이미지 키워드 결과를 지정한 이름으로 데이터베이스에 영구 저장합니다. " +
			"Redis에 임시 저장된 미리보기 데이터를 사용하므로, 미리보기 생성 후 15분 이내에 호출해야 합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON200",
				description = "요청에 성공하였습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ImageKeywordResponseDTO.ImageKeywordSaveResponseDTO.class)
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON401",
				description = "인증이 필요합니다. 액세스 토큰을 입력해주세요.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(example = """
						{
						  "isSuccess": false,
						  "code": "COMMON401",
						  "message": "인증이 필요합니다."
						}
						""")
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "IMAGEKEYWORD4001",
				description = "이미지 키워드 미리보기 결과가 만료되었습니다. 다시 시도해주세요.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(example = """
						{
						  "isSuccess": false,
						  "code": "IMAGEKEYWORD4001",
						  "message": "이미지 키워드 미리보기 결과가 만료되었습니다. 다시 시도해주세요."
						}
						""")
				)
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "IMAGEKEYWORD4002",
				description = "이미 같은 이름으로 저장된 이미지 키워드 결과가 있습니다.",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(example = """
						{
						  "isSuccess": false,
						  "code": "IMAGEKEYWORD4002",
						  "message": "이미 같은 이름으로 저장된 이미지 키워드 결과가 있습니다."
						}
						""")
				)
			)
		}
	)
	ResponseEntity<ApiResponse<ImageKeywordResponseDTO.ImageKeywordSaveResponseDTO>> saveImageKeywordResult(
		@Parameter(hidden = true)
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@Parameter(description = "이미지 키워드 저장 요청", required = true)
		@Validated @RequestBody ImageKeywordRequestDTO.ImageKeywordSaveRequestDTO request
	);
}