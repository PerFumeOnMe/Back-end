package PerfumeOnMe.spring.s3file.web.dto;

import lombok.Builder;
import lombok.Getter;

public class s3ResponseDTO {
	@Getter
	@Builder
	public static class PresignedUrlResponseDTO {
		private final String presignedUrl; // PUT 요청용 URL- 프론트가 PUT 요청으로 업로드할 수 있는 주소
		private final String s3Url;        // 최종 조회 가능한 URL - 업로드가 완료된 후 접근 가능한 이미지 URL
		private final String uuid;         // 내부 DB에 저장된 UUID
	}
}
