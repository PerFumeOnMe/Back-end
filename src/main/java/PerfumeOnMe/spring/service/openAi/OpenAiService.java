package PerfumeOnMe.spring.service.openAi;

public interface OpenAiService {

	// PBTI 구조화된 응답 반환
	String getStructuredResponse(String prompt);
}

