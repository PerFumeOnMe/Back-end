package PerfumeOnMe.spring.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
			.middleNote(request.getMiddleNote())
			.baseNote(request.getBaseNote())
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
			.middleNote(request.getMiddleNote())
			.baseNote(request.getBaseNote())
			.keywordSummary(workshopResult.getKeywordSummary())
			.firstImpression(workshopResult.getFirstImpression())
			.centerImpression(workshopResult.getCenterImpression())
			.lastImpression(workshopResult.getLastImpression())
			.tendency(workshopResult.getTendency())
			.recommendedFragranceJson(recommendedFragranceDTOList)
			.build();
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
