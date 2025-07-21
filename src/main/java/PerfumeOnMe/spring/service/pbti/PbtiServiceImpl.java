package PerfumeOnMe.spring.service.pbti;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.service.openAi.OpenAiService;
import PerfumeOnMe.spring.service.openAi.PromptBuilder;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiRequestDTO;
import PerfumeOnMe.spring.web.dto.Pbti.PbtiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PbtiServiceImpl implements PbtiService {

	private final OpenAiService openAiService;
	private final ObjectMapper objectMapper;

	@Override
	public PbtiResponseDTO.PbtiQuestionResponse searchPbti(Long userId, PbtiRequestDTO.PbtiQuestionRequest request) {

		PbtiResponseDTO.PbtiResult result = PbtiScoringUtil.calculateMbtiType(request);

		String prompt = PromptBuilder.buildPromptFromRequest(request, result);

		// GPT로부터 응답 받기
		String gptResponse = openAiService.getStructuredResponse(prompt);

		// JSON → DTO 역직렬화
		try {
			return objectMapper.readValue(gptResponse, PbtiResponseDTO.PbtiQuestionResponse.class);
		} catch (JsonProcessingException e) {
			log.error("GPT 응답 JSON 파싱 실패. 응답: {}", gptResponse, e);
			throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
		}
	}

}
