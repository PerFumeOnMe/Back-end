package PerfumeOnMe.spring.service.imagekeyword;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.service.external.FastApiClient;
import PerfumeOnMe.spring.service.redis.ImageKeywordRedisService;
import PerfumeOnMe.spring.web.dto.external.FastApiRecommendRequest;
import PerfumeOnMe.spring.web.dto.external.FastApiRecommendResponse;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordRequestDTO.ImageKeywordPreviewRequestDTO;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordResponseDTO.ImageKeywordPreviewResponseDTO.FragranceRecommendation;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageKeywordPreviewService {

	private static final String TEMP_CHARACTER_IMAGE_URL = "https://s3.amazonaws.com/your-bucket/image-keyword/temp-character.png";
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
				.build()
			).collect(Collectors.toList());

		// ✅ 5. 최종 Preview 응답 구성
		ImageKeywordPreviewResponseDTO previewDTO = ImageKeywordPreviewResponseDTO.builder()
			.keywords(keywords)
			.descriptions(descriptions)
			.scenario(fastApiResponse.getScenario())
			.characterImageUrl(TEMP_CHARACTER_IMAGE_URL) // 추후 S3 동적 처리 예정
			.recommendations(recommendations)
			.build();

		// ✅ 6. Redis 저장 (TTL 15분)
		redisService.savePreview(userId, previewDTO);

		return previewDTO;
	}
}