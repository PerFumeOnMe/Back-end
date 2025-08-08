package PerfumeOnMe.spring.converter;

import PerfumeOnMe.spring.domain.Uuid;
import PerfumeOnMe.spring.web.dto.s3.s3ResponseDTO;

public class S3Converter {

	public static s3ResponseDTO.PresignedUrlResponseDTO toPresignedUrlResponseDto(String presignedUrl, String s3Url,
		Uuid uuid) {
		return s3ResponseDTO.PresignedUrlResponseDTO.builder()
			.presignedUrl(presignedUrl)
			.s3Url(s3Url)
			.uuid(uuid.getUuid())
			.build();
	}
}