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
import PerfumeOnMe.spring.repository.imagekeyworddescription.ImageKeywordDescriptionRepository;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.service.redis.ImageKeywordRedisService;
import PerfumeOnMe.spring.util.EnumDisplayNameMapper;
import PerfumeOnMe.spring.util.JsonUtils;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageKeywordServiceImpl implements ImageKeywordService {
	private final ImageKeywordRepository imageKeywordRepository;
	private final UserRepository userRepository;
	private final ImageKeywordDescriptionRepository imageKeywordDescriptionRepository;
	private final ImageKeywordRedisService redisService;

	@Override
	@Transactional(readOnly = true)
	public List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO> getImageKeywordList(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		List<ImageKeyword> imageKeywords = imageKeywordRepository.findAllByUserOrderByCreatedAtDesc(user);

		return ImageKeywordConverter.toImageKeywordListResponse(imageKeywords);
	}

	@Override
	@Transactional(readOnly = true)
	public ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO getImageKeywordDetail(Long userId,
		Long imageKeywordId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		ImageKeyword keyword = imageKeywordRepository.findByIdAndUser(imageKeywordId, user)
			.orElseThrow(() -> new GeneralException(ErrorStatus.INVALID_IMAGEKEYWORD_ID));
		return ImageKeywordConverter.toImageKeywordDetailResponse(keyword, imageKeywordDescriptionRepository);
	}

	@Override
	@Transactional
	public ImageKeywordResponseDTO.ImageKeywordSaveResponseDTO saveImageKeyword(Long userId, String savedName) {
		// 사용자 검증
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));
		// 중복 이름 체크
		if (imageKeywordRepository.existsByUserAndSavedName(user, savedName)) {
			throw new GeneralException(ErrorStatus.ALREADY_KEYWORD_NAME);
		}
		// ✅ Redis에서 미리보기 결과 조회
		ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO cachedPreview = redisService.getPreview(userId);
		if (cachedPreview == null) {
			throw new GeneralException(ErrorStatus.EXPIRED_IMAGEKEYWORD_RESULT); // IK4002
		}

		// ✅ Entity 생성 및 저장
		ImageKeyword entity = ImageKeyword.builder()
			.user(user)
			.savedName(savedName)
			.scenario(cachedPreview.getScenario())
			.imageUrl(cachedPreview.getCharacterImageUrl())
			.ambience(EnumDisplayNameMapper.toAmbience(cachedPreview.getKeywords()))
			.style(EnumDisplayNameMapper.toStyle(cachedPreview.getKeywords()))
			.gender(EnumDisplayNameMapper.toGender(cachedPreview.getKeywords()))
			.season(EnumDisplayNameMapper.toSeason(cachedPreview.getKeywords()))
			.personality(EnumDisplayNameMapper.toPersonality(cachedPreview.getKeywords()))
			.keywordDescription(cachedPreview.getDescriptions())
			.recommendedFragranceJson(JsonUtils.toJson(cachedPreview.getRecommendations()))
			.build();

		ImageKeyword saved = imageKeywordRepository.save(entity);

		// ✅ Redis 키 삭제
		redisService.deletePreview(userId);

		return ImageKeywordConverter.toSaveResponseDTO(saved);

	}
}