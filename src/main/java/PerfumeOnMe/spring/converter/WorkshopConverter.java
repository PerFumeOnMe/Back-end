package PerfumeOnMe.spring.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.Workshop;
import PerfumeOnMe.spring.domain.WorkshopFragrance;
import PerfumeOnMe.spring.service.workshop.WorkshopResult;
import PerfumeOnMe.spring.web.dto.workshop.WorkshopRequestDTO;
import PerfumeOnMe.spring.web.dto.workshop.WorkshopResponseDTO;

public class WorkshopConverter {

	/** 향수공방 목록 converter*/
	public static List<WorkshopResponseDTO.WorkshopListResponseDTO> toWorkshopListResponse(
		List<Workshop> workshops
	) {
		return workshops.stream()
			.map(workshop -> WorkshopResponseDTO.WorkshopListResponseDTO.builder()
				.workshopId(workshop.getId())
				.savedName(workshop.getSavedName())
				.createdAt(workshop.getCreatedAt())
				.build())
			.toList();
	}

	public static WorkshopResponseDTO.WorkshopDetailResponseDTO toWorkshopDetailResponse(Workshop workshop) {
		// 추천 향수 리스트 JSON 파싱
		List<WorkshopResponseDTO.RecommendedFragranceDTO> recommendedFragranceDTOList =
			parseFragranceJson(workshop.getRecommendedFragranceJson());

		return WorkshopResponseDTO.WorkshopDetailResponseDTO.builder()
			.keywordSummary(workshop.getKeywordSummary())
			.firstImpression(workshop.getFirstImpression())
			.centerImpression(workshop.getCenterImpression())
			.lastImpression(workshop.getLastImpression())
			.tendency(workshop.getTendency())
			.recommendedFragranceJson(recommendedFragranceDTOList)
			.build();
	}

	/** 향수공방 미리보기 응답 DTO 생성 */
	public static WorkshopResponseDTO.WorkshopPreviewResponseDTO toWorkshopPreviewResponse(
		WorkshopRequestDTO.WorkshopPreviewRequestDTO request,
		WorkshopResult workshopResult
	) {
		// TODO: 향수 추천 로직은 FastAPI 연동 후 구현 예정
		List<WorkshopResponseDTO.RecommendedFragranceDTO> emptyRecommendations = new ArrayList<>();

		return WorkshopResponseDTO.WorkshopPreviewResponseDTO.builder()
			.topNote(request.getTopNote())
			.topNoteVolume(request.getTopNoteVolume())
			.middleNote(request.getMiddleNote())
			.middleNoteVolume(request.getMiddleNoteVolume())
			.baseNote(request.getBaseNote())
			.baseNoteVolume(request.getBaseNoteVolume())
			.keywordSummary(workshopResult.getKeywordSummary())
			.firstImpression(workshopResult.getFirstImpression())
			.centerImpression(workshopResult.getCenterImpression())
			.lastImpression(workshopResult.getLastImpression())
			.tendency(workshopResult.getTendency())
			.recommendedFragranceJson(emptyRecommendations)
			.build();
	}

	/** 향수공방 미리보기 응답 DTO 생성 (추천 향수 포함) */
	public static WorkshopResponseDTO.WorkshopPreviewResponseDTO toWorkshopPreviewResponse(
		WorkshopRequestDTO.WorkshopPreviewRequestDTO request,
		WorkshopResult workshopResult,
		List<WorkshopFragrance> recommendedFragrances
	) {
		// WorkshopFragrance를 RecommendedFragranceDTO로 변환
		List<WorkshopResponseDTO.RecommendedFragranceDTO> recommendedFragranceDTOList = 
			recommendedFragrances.stream()
				.map(fragrance -> WorkshopResponseDTO.RecommendedFragranceDTO.builder()
					.brand(fragrance.getBrand())
					.name(fragrance.getName())
					.description(fragrance.getDescription())
					.price(fragrance.getPrice())
					.imageUrl(fragrance.getImageUrl())
					.build())
				.collect(Collectors.toList());

		return WorkshopResponseDTO.WorkshopPreviewResponseDTO.builder()
			.topNote(request.getTopNote())
			.topNoteVolume(request.getTopNoteVolume())
			.middleNote(request.getMiddleNote())
			.middleNoteVolume(request.getMiddleNoteVolume())
			.baseNote(request.getBaseNote())
			.baseNoteVolume(request.getBaseNoteVolume())
			.keywordSummary(workshopResult.getKeywordSummary())
			.firstImpression(workshopResult.getFirstImpression())
			.centerImpression(workshopResult.getCenterImpression())
			.lastImpression(workshopResult.getLastImpression())
			.tendency(workshopResult.getTendency())
			.recommendedFragranceJson(recommendedFragranceDTOList)
			.build();
	}

	/** 향수공방 저장 응답 DTO 생성 */
	public static WorkshopResponseDTO.WorkshopSaveResponseDTO toWorkshopSaveResponse(Workshop workshop) {
		return WorkshopResponseDTO.WorkshopSaveResponseDTO.builder()
			.workshopId(workshop.getId())
			.savedName(workshop.getSavedName())
			.createdAt(workshop.getCreatedAt())
			.build();
	}

	/** Redis 미리보기 데이터를 Workshop 엔티티로 변환 */
	public static Workshop toWorkshopEntity(
		User user,
		String savedName,
		WorkshopResponseDTO.WorkshopPreviewResponseDTO previewData,
		String recommendedFragranceJson
	) {
		return Workshop.builder()
			.user(user)
			.savedName(savedName)
			.topNote(previewData.getTopNote())
			.topNoteVolume(previewData.getTopNoteVolume())
			.middleNote(previewData.getMiddleNote())
			.middleNoteVolume(previewData.getMiddleNoteVolume())
			.baseNote(previewData.getBaseNote())
			.baseNoteVolume(previewData.getBaseNoteVolume())
			.keywordSummary(previewData.getKeywordSummary())
			.firstImpression(previewData.getFirstImpression())
			.centerImpression(previewData.getCenterImpression())
			.lastImpression(previewData.getLastImpression())
			.tendency(previewData.getTendency())
			.recommendedFragranceJson(recommendedFragranceJson)
			.build();
	}

	/** 추천 향수 리스트를 JSON 문자열로 변환 */
	public static String toRecommendedFragranceJson(List<WorkshopResponseDTO.RecommendedFragranceDTO> fragrances) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(fragrances);
		} catch (JsonProcessingException e) {
			return "[]"; // 빈 배열 반환
		}
	}

	/** JSON 문자열을 추천 향수 DTO 리스트로 변환 */
	private static List<WorkshopResponseDTO.RecommendedFragranceDTO> parseFragranceJson(String fragranceJson) {
		if (fragranceJson == null || fragranceJson.trim().isEmpty()) {
			return new ArrayList<>();
		}

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(fragranceJson,
				new TypeReference<List<WorkshopResponseDTO.RecommendedFragranceDTO>>() {
				});
		} catch (JsonProcessingException e) {
			return new ArrayList<>();
		}
	}

}
