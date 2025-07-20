package PerfumeOnMe.spring.service.imagekeyword;

import java.util.List;

import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordResponseDTO;

public interface ImageKeywordService {
	List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO> getImageKeywordList(Long userId);

	ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO getImageKeywordDetail(Long userId, Long imageKeywordId);
}
