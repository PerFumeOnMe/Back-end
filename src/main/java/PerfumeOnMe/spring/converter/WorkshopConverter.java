package PerfumeOnMe.spring.converter;

import java.util.List;

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
}
