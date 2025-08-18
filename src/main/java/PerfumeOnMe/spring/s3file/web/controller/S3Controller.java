package PerfumeOnMe.spring.s3file.web.controller;

import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.s3file.aws.AmazonS3Manager;
import PerfumeOnMe.spring.s3file.converter.S3Converter;
import PerfumeOnMe.spring.s3file.web.docs.S3ControllerDocs;
import PerfumeOnMe.spring.s3file.web.dto.s3RequestDTO;
import PerfumeOnMe.spring.s3file.web.dto.s3ResponseDTO;
import PerfumeOnMe.spring.uuid.domain.Uuid;
import PerfumeOnMe.spring.uuid.repository.UuidRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller implements S3ControllerDocs {

	private final AmazonS3Manager amazonS3Manager;
	private final UuidRepository uuidRepository;

	@PostMapping("/upload-url")
	public ResponseEntity<ApiResponse<s3ResponseDTO.PresignedUrlResponseDTO>> generatePresignedUrl(
		@RequestBody s3RequestDTO.PresignedUrlRequestDTO request // ✅ 요청 DTO 클래스를 요청 전용 클래스로 변경
	) {
		String fileName = request.getFileName();
		String ext = fileName.substring(fileName.lastIndexOf('.') + 1); // ✅ 파일명에서 확장자 추출

		// 확장자 유효성 검사
		List<String> allowedExtensions = List.of("png", "jpg", "jpeg", "webp");
		if (!allowedExtensions.contains(ext.toLowerCase())) {
			throw new GeneralException(ErrorStatus.INVALID_IMAGE_EXTENSION);
		}

		// UUID 생성 및 저장
		Uuid uuid = uuidRepository.save(Uuid.builder()
			.uuid(UUID.randomUUID().toString())
			.build());

		// Presigned URL 생성
		long expirationMillis = 10 * 60 * 1000;
		URL presignedUrl = amazonS3Manager.generatePresignedUploadUrl(uuid, expirationMillis, ext);

		// S3 접근 URL 구성
		String s3Url = "https://" + amazonS3Manager.getBucket()
			+ ".s3." + amazonS3Manager.getRegion()
			+ ".amazonaws.com/" + amazonS3Manager.getProfilePath()
			+ "/" + uuid.getUuid() + "." + ext;

		// DTO 변환 및 응답
		s3ResponseDTO.PresignedUrlResponseDTO result = S3Converter.toPresignedUrlResponseDto(
			presignedUrl.toString(), s3Url, uuid
		);

		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}