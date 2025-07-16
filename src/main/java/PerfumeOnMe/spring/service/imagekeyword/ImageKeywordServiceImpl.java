package PerfumeOnMe.spring.service.imagekeyword;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.converter.ImageKeywordConverter;
import PerfumeOnMe.spring.domain.ImageKeyword;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.repository.imagekeyword.ImageKeywordRepository;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageKeywordServiceImpl implements ImageKeywordService {
	private final ImageKeywordRepository imageKeywordRepository;
	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO> getImageKeywordList(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		List<ImageKeyword> imageKeywords = imageKeywordRepository.findAllByUserOrderByCreatedAtDesc(user);

		return ImageKeywordConverter.toImageKeywordListResponse(imageKeywords);
	}
}
