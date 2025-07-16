package PerfumeOnMe.spring.converter;

import java.util.List;

import PerfumeOnMe.spring.domain.ImageKeyword;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordResponseDTO;

public class ImageKeywordConverter {

	public static List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO> toImageKeywordListResponse(
		List<ImageKeyword> imageKeywords) {
		return imageKeywords.stream()
			.map(imageKeyword -> ImageKeywordResponseDTO.ImageKeywordListResponseDTO.builder()
				.imageKeywordId(imageKeyword.getId())
				.savedName(imageKeyword.getSavedName())
				.createdAt(imageKeyword.getCreatedAt())
				.build())
			.toList();
	}
}
