package PerfumeOnMe.spring.service.pbti;

import PerfumeOnMe.spring.web.dto.Pbti.PbtiRequestDTO;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiResponseDTO;

public interface PbtiService {

	// PBTI 결과 조회 API
	PbtiResponseDTO.PbtiQuestionResponse searchPbti(Long userId, PbtiRequestDTO.PbtiQuestionRequest request);

	// PBTI 결과 저장 API
	PbtiResponseDTO.PbtiSaveResponse savePbti(Long userId, PbtiRequestDTO.PbtiSaveRequest request);

	// 마이페이지 PBTI 목록 조회 API
	PbtiResponseDTO.SearchPbtiListResponse searchPbtiList(Long userId);

	// 마이페이지 PBTI 결과 상세 조회 API
	PbtiResponseDTO.PbtiResultDetailResponse searchPbtiResult(Long userId,
		PbtiRequestDTO.PbtiResultDetailRequest request);

	// PBTI 결과 이름 수정 API
	PbtiResponseDTO.UpdatePbtiNameResponse updatePbtiName(Long userId, Long pbtiId,
		PbtiRequestDTO.UpdatePbtiNameRequest request);

	// PBTI 결과 삭제 API
	Void deletePbtiResult(Long userId, Long pbtiId);
}
