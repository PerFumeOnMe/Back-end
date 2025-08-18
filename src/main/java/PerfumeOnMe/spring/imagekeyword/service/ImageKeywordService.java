package PerfumeOnMe.spring.imagekeyword.service;

import java.util.List;

import PerfumeOnMe.spring.imagekeyword.web.dto.ImageKeywordResponseDTO;

public interface ImageKeywordService {
	List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO> getImageKeywordList(Long userId);

	ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO getImageKeywordDetail(Long userId, Long imageKeywordId);

	ImageKeywordResponseDTO.ImageKeywordSaveResponseDTO saveImageKeyword(Long userId, String savedName);
}