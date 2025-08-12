package PerfumeOnMe.spring.external.openai;

public interface OpenAiService {

	// PBTI 구조화된 응답 반환
	String getStructuredResponse(String prompt);

	// 향수공방 결과 생성
	String generateWorkshopResult(String topNote, Long topNoteVolume,
		String middleNote, Long middleNoteVolume,
		String baseNote, Long baseNoteVolume);
}

