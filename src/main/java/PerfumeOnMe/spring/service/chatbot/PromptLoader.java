package PerfumeOnMe.spring.service.chatbot;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;

@Component
public class PromptLoader {

	private final String defaultPromptFile = "expert.txt";

	public String loadDefaultPrompt() {
		try (InputStream is = getClass().getClassLoader().getResourceAsStream("prompts/" + defaultPromptFile)) {
			if (is == null)
				throw new GeneralException(ErrorStatus.FILE_NOT_FOUND); // 파일을 찾을 수 없음
			return new String(is.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new GeneralException(ErrorStatus.PROMPT_LOADING_FAIL); // 프롬프트 로딩 실패
		}
	}
}
