package PerfumeOnMe.spring.imagekeyword.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.common.util.CharacterImageMapper;
import PerfumeOnMe.spring.external.fastapi.FastApiClient;
import PerfumeOnMe.spring.external.fastapi.dto.FastApiRecommendRequest;
import PerfumeOnMe.spring.external.fastapi.dto.FastApiRecommendResponse;
import PerfumeOnMe.spring.imagekeyword.redis.ImageKeywordRedisService;
import PerfumeOnMe.spring.imagekeyword.web.dto.ImageKeywordRequestDTO.ImageKeywordPreviewRequestDTO;
import PerfumeOnMe.spring.imagekeyword.web.dto.ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO;
import PerfumeOnMe.spring.imagekeyword.web.dto.ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO.FragranceRecommendation;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageKeywordPreviewService {

	private final ImageKeywordDescriptionService descriptionService;
	private final FastApiClient fastApiClient;
	private final ImageKeywordRedisService redisService;

	/**
	 * 키워드 기반 감성 시나리오 + 향수 추천 결과 생성 후 Redis에 저장
	 */
	public ImageKeywordPreviewResponseDTO generatePreview(Long userId, ImageKeywordPreviewRequestDTO request) {

		// ✅ 1. 키워드 리스트
		List<String> keywords = List.of(
			request.getAmbience(), request.getStyle(), request.getSeason(),
			request.getPersonality(), request.getGender()
		);

		// ✅ 2. 설명 조합
		String descriptions = descriptionService.getDescriptions(
			request.getAmbience(),
			request.getStyle(),
			request.getGender(),
			request.getSeason(),
			request.getPersonality()
		);

		// ✅ 3. FastAPI 추천 요청
		FastApiRecommendRequest fastApiRequest = new FastApiRecommendRequest(
			request.getAmbience(),
			request.getStyle(),
			request.getGender(),
			request.getSeason(),
			request.getPersonality()
		);

		FastApiRecommendResponse fastApiResponse = fastApiClient.getFullRecommendation(fastApiRequest);

		// ✅ 4. 추천 향수 매핑
		List<FragranceRecommendation> recommendations = fastApiResponse.getRecommendations().stream()
			.map(f -> FragranceRecommendation.builder()
				.brand(f.getBrand())
				.name(f.getName())
				.topNote(f.getTopNote())
				.middleNote(f.getMiddleNote())
				.baseNote(f.getBaseNote())
				.description(f.getDescription())
				.relatedKeywords(f.getRelatedKeywords())
				.imageUrl(f.getImageUrl())
				.removebgImageUrl(f.getRemovebgImageUrl())
				.build()
			).collect(Collectors.toList());

		// ✅ 5. 분위기에 따른 감성 캐릭터 이미지 URL 선택
		String characterImageUrl = CharacterImageMapper.getCharacterImageUrl(request.getAmbience());

		// ✅ 6. 최종 Preview 응답 구성
		ImageKeywordPreviewResponseDTO previewDTO = ImageKeywordPreviewResponseDTO.builder()
			.keywords(keywords)
			.descriptions(descriptions)
			.scenario(fastApiResponse.getScenario())
			.characterImageUrl(characterImageUrl)
			.recommendations(recommendations)
			.build();

		// ✅ 7. Redis 저장 (TTL 15분)
		redisService.savePreview(userId, previewDTO);

		return previewDTO;
	}
}