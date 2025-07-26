package PerfumeOnMe.spring.service.pbti;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.converter.PbtiConverter;
import PerfumeOnMe.spring.domain.PBTI;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.repository.pbti.PbtiRepository;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.service.external.FastApiClient;
import PerfumeOnMe.spring.service.openAi.OpenAiService;
import PerfumeOnMe.spring.service.openAi.PromptBuilder;
import PerfumeOnMe.spring.util.JsonUtils;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiRequestDTO;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiResponseDTO;
import PerfumeOnMe.spring.web.dto.external.FastApiPbtiRecommendResponse;
import PerfumeOnMe.spring.web.dto.external.FastApiRecommendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PbtiServiceImpl implements PbtiService {

	private final OpenAiService openAiService;
	private final ObjectMapper objectMapper;
	private final PbtiRepository pbtiRepository;
	private final UserRepository userRepository;
	private final StringRedisTemplate stringRedisTemplate;
	private final FastApiClient fastApiClient;

	// PBTI 결과 조회 API
	@Override
	public PbtiResponseDTO.PbtiQuestionResponse searchPbti(Long userId, PbtiRequestDTO.PbtiQuestionRequest request) {

		PbtiResponseDTO.PbtiResult result = PbtiScoringUtil.calculateMbtiType(request);

		String prompt = PromptBuilder.buildPromptFromRequest(request, result);

		// GPT로부터 응답 받기
		String gptResponse = openAiService.getStructuredResponse(prompt);

		// JSON → DTO 역직렬화
		PbtiResponseDTO.PbtiQuestionResponse response;
		try {
			response = objectMapper.readValue(gptResponse, PbtiResponseDTO.PbtiQuestionResponse.class);
		} catch (JsonProcessingException e) {
			log.error("GPT 응답 JSON 파싱 실패. 응답: {}", gptResponse, e);
			throw new GeneralException(ErrorStatus.JSON_PARSING_ERROR);
		}

		// FastAPI 호출로 perfumeRecommend 대체
		FastApiRecommendRequest.PbtiRequest fastApiRequest = new FastApiRecommendRequest.PbtiRequest(
			request.getQOne(),
			request.getQTwo(),
			request.getQThree(),
			request.getQFour(),
			request.getQFive(),
			request.getQSix(),
			request.getQSeven(),
			request.getQEight()
		);

		FastApiPbtiRecommendResponse fastApiResponse = fastApiClient.getPbtiRecommendation(fastApiRequest);

		// ⬇ FastAPI 향수 추천 결과 매핑
		List<PbtiResponseDTO.PbtiQuestionResponse.PerfumeRecommend> mappedPerfumes = fastApiResponse.getPerfumeRecommend()
			.stream()
			.map(r -> PbtiResponseDTO.PbtiQuestionResponse.PerfumeRecommend.builder()
				.name(r.getName())
				.brand(r.getBrand())
				.description(r.getDescription())
				.perfumeImageUrl(r.getPerfumeImageUrl())
				.build())
			.collect(Collectors.toList());

		// 결과 세팅
		response.setPerfumeRecommend(mappedPerfumes);

		// Redis에 저장할 DTO 생성
		PbtiResponseDTO.PbtiRedisDTO redisDTO = PbtiResponseDTO.PbtiRedisDTO.builder()
			.qOne(request.getQOne())
			.qTwo(request.getQTwo())
			.qThree(request.getQThree())
			.qFour(request.getQFour())
			.qFive(request.getQFive())
			.qSix(request.getQSix())
			.qSeven(request.getQSeven())
			.qEight(request.getQEight())
			.recommendation(response.getRecommendation())
			.summary(response.getSummary())
			.keywords(response.getKeywords())
			.perfumeStyle(response.getPerfumeStyle())
			.scentPoint(response.getScentPoint())
			.perfumeRecommend(response.getPerfumeRecommend())
			.build();

		// Redis에 저장
		try {
			String redisKey = "pbti:result:" + userId;
			String json = objectMapper.writeValueAsString(redisDTO);
			stringRedisTemplate.opsForValue().set(redisKey, json, Duration.ofMinutes(15));
		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.SAVE_REDIS_ERROR);
		}

		return response;
	}

	// PBTI 결과 저장 API
	@Override
	public PbtiResponseDTO.PbtiSaveResponse savePbti(Long userId, PbtiRequestDTO.PbtiSaveRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		// Redis에서 결과 JSON 조회
		String redisKey = "pbti:result:" + userId;
		String resultJson = stringRedisTemplate.opsForValue().get(redisKey);
		if (resultJson == null) {
			throw new GeneralException(ErrorStatus.PBTI_REDIS_KEY_EXPIRED);
		}

		// JSON → DTO 역직렬화
		PbtiResponseDTO.PbtiRedisDTO redisResult;
		try {
			redisResult = objectMapper.readValue(resultJson, PbtiResponseDTO.PbtiRedisDTO.class);
		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}

		PBTI pbti = PBTI.builder()
			.user(user)
			.savedName(request.getSavedName())
			.qOne(redisResult.getQOne())
			.qTwo(redisResult.getQTwo())
			.qThree(redisResult.getQThree())
			.qFour(redisResult.getQFour())
			.qFive(redisResult.getQFive())
			.qSix(redisResult.getQSix())
			.qSeven(redisResult.getQSeven())
			.qEight(redisResult.getQEight())
			.recommendation(redisResult.getRecommendation())
			.summary(redisResult.getSummary())
			.keywords(JsonUtils.toJson(redisResult.getKeywords()))
			.perfumeStyle(JsonUtils.toJson(redisResult.getPerfumeStyle()))
			.scentPoint(JsonUtils.toJson(redisResult.getScentPoint()))
			.perfumeRecommend(JsonUtils.toJson(redisResult.getPerfumeRecommend()))
			.build();

		PBTI saved = pbtiRepository.save(pbti);

		// Redis 삭제
		stringRedisTemplate.delete(redisKey);

		return PbtiConverter.toPbtiSaveResponse(saved);
	}

	// 마이페이지 PBTI 목록 조회 API
	@Override
	public PbtiResponseDTO.SearchPbtiListResponse searchPbtiList(Long userId) {
		List<PBTI> pbtiList = pbtiRepository.findAllByUserId(userId);
		List<PbtiResponseDTO.PbtiListResult> results = pbtiList.stream()
			.map(PbtiConverter::toPbtiListResult)
			.collect(Collectors.toList());

		return PbtiResponseDTO.SearchPbtiListResponse.builder()
			.result(results)
			.build();
	}

	// 마이페이지 PBTI 결과 상세 조회 API
	@Override
	public PbtiResponseDTO.PbtiResultDetailResponse searchPbtiResult(Long userId,
		PbtiRequestDTO.PbtiResultDetailRequest request) {
		PBTI pbti = pbtiRepository.findById(request.getPbtiId())
			.orElseThrow(() -> new GeneralException(ErrorStatus.PBTI_NOT_EXIST_ERROR));

		if (!pbti.getUser().getId().equals(userId)) {
			throw new GeneralException(ErrorStatus.PBTI_USER_NOT_MATCH);
		}

		return PbtiConverter.toPbtiResultDetailResponse(pbti);
	}

}
