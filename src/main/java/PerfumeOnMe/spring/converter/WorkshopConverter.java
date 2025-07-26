package PerfumeOnMe.spring.converter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.domain.Workshop;
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
