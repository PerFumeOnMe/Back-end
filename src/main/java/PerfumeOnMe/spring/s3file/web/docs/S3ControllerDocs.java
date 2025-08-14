package PerfumeOnMe.spring.s3file.web.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.s3file.web.dto.s3RequestDTO;
import PerfumeOnMe.spring.s3file.web.dto.s3ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "S3 Presigned URL", description = "S3 프로필 이미지 업로드용 Presigned URL 발급 API")
public interface S3ControllerDocs {

	@Operation(
		summary = "Presigned URL 발급",
		description = "프로필 이미지 업로드를 위한 S3 Presigned URL을 생성합니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "COMMON200",
				description = "Presigned URL 발급 성공",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = s3ResponseDTO.PresignedUrlResponseDTO.class))
			),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "S3IMAGE4001",
				description = "지원하지 않은 파일 확장자입니다.",
				content = @Content(mediaType = "application/json")
			)
		}
	)
	ResponseEntity<ApiResponse<s3ResponseDTO.PresignedUrlResponseDTO>> generatePresignedUrl(
		@RequestBody s3RequestDTO.PresignedUrlRequestDTO request);
}