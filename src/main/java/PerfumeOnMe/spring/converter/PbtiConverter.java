package PerfumeOnMe.spring.converter;

import java.time.LocalDateTime;

import PerfumeOnMe.spring.domain.PBTI;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiResponseDTO;

public class PbtiConverter {

	// PBTI 결과 저장 API
	public static PbtiResponseDTO.PbtiSaveResponse toPbtiSaveResponse(PBTI pbti) {
		return PbtiResponseDTO.PbtiSaveResponse.builder()
			.id(pbti.getId())
			.savedName(pbti.getSavedName())
			.createdAt(LocalDateTime.from(pbti.getCreatedAt()))
			.build();
	}
}
