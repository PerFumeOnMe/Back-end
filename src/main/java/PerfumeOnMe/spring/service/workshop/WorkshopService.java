package PerfumeOnMe.spring.service.workshop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.converter.WorkshopConverter;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.Workshop;
import PerfumeOnMe.spring.domain.WorkshopFragrance;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.repository.workshop.WorkshopRepository;
import PerfumeOnMe.spring.service.openAi.OpenAiService;
import PerfumeOnMe.spring.service.redis.WorkshopRedisService;
import PerfumeOnMe.spring.service.user.UserService;
import PerfumeOnMe.spring.web.dto.workshop.WorkshopRequestDTO;
import PerfumeOnMe.spring.web.dto.workshop.WorkshopResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkshopService {
	private final WorkshopRepository workshopRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final OpenAiService openAiService;
	private final WorkshopRedisService workshopRedisService;
	private final WorkshopRecommendationService workshopRecommendationService;

	@Transactional(readOnly = true)
	public List<WorkshopResponseDTO.WorkshopListResponseDTO> findAllWorkshopsByUser(CustomUserDetails userDetails) {

		Long userId = userDetails.getUserId();

		// 유저 ID 검증
		if (userId == null) {
			throw new GeneralException(ErrorStatus.USER_ID_NULL);
		}

		// 유저 존재 여부 검증
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		// 응답생성
		List<Workshop> workshops = workshopRepository.findAllByUser(user);

		return WorkshopConverter.toWorkshopListResponse(workshops);
	}

	@Transactional
	public WorkshopResponseDTO.WorkshopSaveResponseDTO saveWorkshop(
		WorkshopRequestDTO.WorkshopSaveRequestDTO request, CustomUserDetails userDetails
	) {
		Long userId = userDetails.getUserId();

		// 유저 ID 검증
		if (userId == null) {
			throw new GeneralException(ErrorStatus.USER_ID_NULL);
		}

		// 유저 존재 여부 검증
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		// savedName 중복 검증
		if (workshopRepository.existsByUserAndSavedName(user, request.getSavedName())) {
			throw new GeneralException(ErrorStatus.WORKSHOP_NAME_DUPLICATE);
		}

		log.info("향수공방 결과 저장 시작 - 사용자 ID: {}, 저장 이름: {}", userId, request.getSavedName());

		// Redis에서 미리보기 결과 조회
		WorkshopResponseDTO.WorkshopPreviewResponseDTO previewData = 
			workshopRedisService.getPreview(userId);

		// 추천 향수 리스트 JSON 직렬화
		String recommendedFragranceJson = WorkshopConverter.toRecommendedFragranceJson(
			previewData.getRecommendedFragranceJson()
		);

		// Workshop 엔티티 생성 및 저장
		Workshop workshop = WorkshopConverter.toWorkshopEntity(
			user, 
			request.getSavedName(), 
			previewData, 
			recommendedFragranceJson
		);
		
		Workshop savedWorkshop = workshopRepository.save(workshop);

		// Redis 임시 데이터 삭제
		workshopRedisService.deletePreview(userId);

		log.info("향수공방 결과 저장 완료 - 사용자 ID: {}, 워크샵 ID: {}", userId, savedWorkshop.getId());

		// 응답 DTO 생성
		return WorkshopConverter.toWorkshopSaveResponse(savedWorkshop);
	}

	@Transactional(readOnly = true)
	public WorkshopResponseDTO.WorkshopDetailResponseDTO findWorkshopById(
		Long workshopId, CustomUserDetails userDetails) {
		Long userId = userDetails.getUserId();

		// 유저 ID NULL 검증
		if (userId == null) {
			throw new GeneralException(ErrorStatus.USER_ID_NULL);
		}

		// 유저 존재 여부 검증
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		// 향수 공방 존재 여부 검증
		if (!workshopRepository.existsById(workshopId)) {
			throw new GeneralException(ErrorStatus.WORKSHOP_ID_NULL);
		}
		// 사용자의 향수공방 여부 검증
		Workshop workshop = workshopRepository.findByIdAndUser(workshopId, user)
			.orElseThrow(() -> new GeneralException(ErrorStatus.WORKSHOP_USER_NOT_MATCH));

		// 응답 생성
		return WorkshopConverter.toWorkshopDetailResponse(workshop);
	}

	@Transactional
	public WorkshopResponseDTO.WorkshopPreviewResponseDTO createWorkshopPreview(
		WorkshopRequestDTO.WorkshopPreviewRequestDTO request, CustomUserDetails userDetails
	) {
		Long userId = userDetails.getUserId();

		// 유저 ID NULL 검증
		if (userId == null) {
			throw new GeneralException(ErrorStatus.USER_ID_NULL);
		}

		// 유저 존재 여부 검증
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		// 용량 검증 (3개의 총 합이 10 이하인지를 검증)
		Long totalNoteVolume = request.getTopNoteVolume() + request.getMiddleNoteVolume() + request.getBaseNoteVolume();
		if (totalNoteVolume > 10) {
			throw new GeneralException(ErrorStatus.WORKSHOP_TOTAL_VOLUME_OVERFLOW);
		}

		// 서비스 동작
		log.info("향수공방 미리보기 생성 시작 - 사용자 ID: {}", userId);

		// GPT API를 통한 향수공방 결과 생성
		String gptResult = openAiService.generateWorkshopResult(
			request.getTopNote(), request.getTopNoteVolume(),
			request.getMiddleNote(), request.getMiddleNoteVolume(),
			request.getBaseNote(), request.getBaseNoteVolume()
		);

		// GPT 응답 파싱
		WorkshopResultParser parser = new WorkshopResultParser();
		WorkshopResult workshopResult = parser.parseGptResponse(gptResult);

		log.info("향수공방 미리보기 생성 완료 - 사용자 ID: {}, 키워드: {}", userId, workshopResult.getKeywordSummary());

		// 향수 추천 생성 (노트 정보를 추천 서비스용 형태로 변환)
		WorkshopRequestDTO.WorkshopCreateRequestDTO recommendRequest =
			convertToRecommendRequest(request);
		List<WorkshopFragrance> recommendedFragrances =
			workshopRecommendationService.recommendFragrances(recommendRequest);

		log.info("향수 추천 완료 - 추천된 향수 개수: {}", recommendedFragrances.size());

		// 응답 DTO 생성 (추천 향수 포함)
		WorkshopResponseDTO.WorkshopPreviewResponseDTO response =
			WorkshopConverter.toWorkshopPreviewResponse(request, workshopResult, recommendedFragrances);

		// Redis에 미리보기 결과 저장 (15분 TTL)
		workshopRedisService.savePreview(userId, response);

		return response;
	}

	/**
	 * WorkshopPreviewRequestDTO를 WorkshopCreateRequestDTO로 변환
	 */
	private WorkshopRequestDTO.WorkshopCreateRequestDTO convertToRecommendRequest(
		WorkshopRequestDTO.WorkshopPreviewRequestDTO request) {

		Map<String, Integer> topNoteMap = new HashMap<>();
		topNoteMap.put(request.getTopNote(), request.getTopNoteVolume().intValue());

		Map<String, Integer> middleNoteMap = new HashMap<>();
		middleNoteMap.put(request.getMiddleNote(), request.getMiddleNoteVolume().intValue());

		Map<String, Integer> baseNoteMap = new HashMap<>();
		baseNoteMap.put(request.getBaseNote(), request.getBaseNoteVolume().intValue());

		return WorkshopRequestDTO.WorkshopCreateRequestDTO.builder()
			.topNoteList(topNoteMap)
			.middleNoteList(middleNoteMap)
			.baseNoteList(baseNoteMap)
			.build();
	}

}
