package PerfumeOnMe.spring.service.openAi;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OpenAiServiceImpl implements OpenAiService {

	private final OpenAiApiClient openAiApiClient; // GPT API 호출용 클라이언트

	@Override
	public String getStructuredResponse(String prompt) {
		return openAiApiClient.callChatGPT(prompt); // 실제 GPT 호출 로직 구현
	}
	
	@Override
	public String generateWorkshopResult(String topNote, Long topNoteVolume, 
	                                   String middleNote, Long middleNoteVolume,
	                                   String baseNote, Long baseNoteVolume) {
		try {
			// 향수공방 프롬프트 파일 읽기
			ClassPathResource resource = new ClassPathResource("prompts/workshop.txt");
			String promptTemplate = Files.readString(Paths.get(resource.getURI()));
			
			// 프롬프트에 사용자 입력값 주입
			String prompt = promptTemplate
				.replace("{topNoteType}", topNote)
				.replace("{topNoteVolume}", String.valueOf(topNoteVolume))
				.replace("{middleNoteType}", middleNote)
				.replace("{middleNoteVolume}", String.valueOf(middleNoteVolume))
				.replace("{baseNoteType}", baseNote)
				.replace("{baseNoteVolume}", String.valueOf(baseNoteVolume));
			
			log.info("향수공방 GPT 요청: topNote={} ({}), middleNote={} ({}), baseNote={} ({})", 
			         topNote, topNoteVolume, middleNote, middleNoteVolume, baseNote, baseNoteVolume);
			
			// GPT API 호출
			String result = openAiApiClient.callChatGPT(prompt);
			
			log.info("향수공방 GPT 응답 생성 완료");
			return result;
			
		} catch (Exception e) {
			log.error("향수공방 GPT 응답 생성 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("향수공방 결과 생성에 실패했습니다.", e);
		}
	}
}
