package PerfumeOnMe.spring.pbti.converter;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.pbti.domain.PBTI;
import PerfumeOnMe.spring.pbti.web.dto.PbtiResponseDTO;

public class PbtiConverter {

	private static final ObjectMapper objectMapper = new ObjectMapper();

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

	// 마이페이지 PBTI 결과 상세 조회 API
	public static PbtiResponseDTO.PbtiResultDetailResponse toPbtiResultDetailResponse(PBTI pbti) {
		try {
			// JSON 파싱
			List<PbtiResponseDTO.PbtiResultDetailResponse.Keyword> keywords =
				objectMapper.readValue(pbti.getKeywords(), new TypeReference<>() {
				});

			PbtiResponseDTO.PbtiResultDetailResponse.PerfumeStyle perfumeStyle =
				objectMapper.readValue(pbti.getPerfumeStyle(),
					PbtiResponseDTO.PbtiResultDetailResponse.PerfumeStyle.class);

			List<PbtiResponseDTO.PbtiResultDetailResponse.ScentPoint> scentPoints =
				objectMapper.readValue(pbti.getScentPoint(), new TypeReference<>() {
				});

			List<PbtiResponseDTO.PbtiResultDetailResponse.PerfumeRecommend> perfumeRecommends =
				objectMapper.readValue(pbti.getPerfumeRecommend(), new TypeReference<>() {
				});

			// DTO 반환
			return PbtiResponseDTO.PbtiResultDetailResponse.builder()
				.savedName(pbti.getSavedName())
				.recommendation(pbti.getRecommendation())
				.keywords(keywords)
				.perfumeStyle(perfumeStyle)
				.scentPoint(scentPoints)
				.summary(pbti.getSummary())
				.perfumeRecommend(perfumeRecommends)
				.build();

		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}
	}

	// PBTI 결과 이름 수정 API
	public static PbtiResponseDTO.UpdatePbtiNameResponse toUpdatePbtiNameResponse(PBTI pbti) {
		return PbtiResponseDTO.UpdatePbtiNameResponse.builder()
			.id(pbti.getId())
			.savedName(pbti.getSavedName())
			.build();
	}
}