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

	// 마이페이지 PBTI 목록 조회 API
	public static PbtiResponseDTO.PbtiListResult toPbtiListResult(PBTI pbti) {
		return PbtiResponseDTO.PbtiListResult.builder()
			.id(pbti.getId())
			.savedName(pbti.getSavedName())
			.createdAt(pbti.getCreatedAt())
			.build();
	}
}
