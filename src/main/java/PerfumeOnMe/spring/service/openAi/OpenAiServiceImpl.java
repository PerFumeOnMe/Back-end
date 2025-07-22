package PerfumeOnMe.spring.service.openAi;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OpenAiServiceImpl implements OpenAiService {

	private final OpenAiApiClient openAiApiClient; // GPT API 호출용 클라이언트

	@Override
	public String getStructuredResponse(String prompt) {
		return openAiApiClient.callChatGPT(prompt); // 실제 GPT 호출 로직 구현
	}
}
