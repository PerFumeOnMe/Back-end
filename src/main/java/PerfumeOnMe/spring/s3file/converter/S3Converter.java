package PerfumeOnMe.spring.s3file.converter;

import PerfumeOnMe.spring.s3file.web.dto.s3ResponseDTO;
import PerfumeOnMe.spring.uuid.domain.Uuid;

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