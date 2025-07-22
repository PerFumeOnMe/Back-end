package PerfumeOnMe.spring.service.pbti;

import PerfumeOnMe.spring.web.dto.Pbti.PbtiRequestDTO;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiResponseDTO;

public interface PbtiService {

	// PBTI 결과 조회 API
	PbtiResponseDTO.PbtiQuestionResponse searchPbti(Long userId, PbtiRequestDTO.PbtiQuestionRequest request);
}
