package PerfumeOnMe.spring.web.controller;

import java.net.URL;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.aws.s3.AmazonS3Manager;
import PerfumeOnMe.spring.domain.Uuid;
import PerfumeOnMe.spring.repository.uuid.UuidRepository;
import PerfumeOnMe.spring.web.dto.s3.s3ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "S3 Presigned URL", description = "S3 프로필 이미지 업로드용 Presigned URL 발급 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileImageController {

	private final AmazonS3Manager amazonS3Manager;
	private final UuidRepository uuidRepository;

	@Operation(summary = "Presigned URL 발급", description = "프로필 이미지 업로드를 위한 S3 Presigned URL을 생성합니다.")
	@GetMapping("/upload-url")
	public ResponseEntity<s3ResponseDTO.PresignedUrlResponseDTO> generatePresignedUrl(
		@Parameter(description = "파일 확장자 (예: png, jpg)", example = "png")
		@RequestParam("ext") String ext // 확장자 입력 받음
	) {
		// 1. 고유 UUID 생성 및 DB에 저장
		Uuid uuid = uuidRepository.save(Uuid.builder()
			.uuid(UUID.randomUUID().toString())
			.build());

		// 2. Presigned URL 생성 (10분 유효)
		long expirationMillis = 10 * 60 * 1000;
		URL presignedUrl = amazonS3Manager.generatePresignedUploadUrl(uuid, expirationMillis, ext);

		// 3. 최종 업로드 완료 후 접근 가능한 S3 URL 생성
		// 3. 최종 S3 접근 URL 생성
		String s3Url = "https://" + amazonS3Manager.getBucket()  // 버킷 이름
			+ ".s3." + amazonS3Manager.getRegion()  // 리전
			+ ".amazonaws.com/" + amazonS3Manager.getProfilePath()  // 경로 (예: user_profiles)
			+ "/" + uuid.getUuid() + "." + ext;  // 파일명 + 확장자

		// 4. 응답 객체 생성
		s3ResponseDTO.PresignedUrlResponseDTO response = s3ResponseDTO.PresignedUrlResponseDTO.builder()
			.presignedUrl(presignedUrl.toString()) // PUT 요청할 presigned URL
			.s3Url(s3Url)                          // 업로드된 이미지 접근용 URL
			.uuid(uuid.getUuid())                  // 추후 추적용 UUID
			.build();

		// 5. 응답 반환
		return ResponseEntity.ok(response);
	}
}
