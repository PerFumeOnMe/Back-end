package PerfumeOnMe.spring.external.openai;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.pbti.web.dto.ChatGptRequest;
import PerfumeOnMe.spring.pbti.web.dto.ChatGptResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OpenAiApiClient {

	private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
	private final WebClient webClient;

	@Value("${openai.api-key}")
	private String openAiApiKey;
	@Value("${openai.model}")
	private String model; // OpenAI 모델 이름 - gpt-4

	public ChatGptResponse getChatGptResponse(ChatGptRequest request) {
		return webClient.post()
			.uri(OPENAI_API_URL)
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.bodyValue(request)
			.retrieve()
			.bodyToMono(ChatGptResponse.class)
			.onErrorResume(e -> Mono.error(new GeneralException(ErrorStatus.CALL_WEBCLIENT_ERROR)))
			.block();
	}

	public String callChatGPT(String prompt) {
		ChatGptRequest request = ChatGptRequest.builder()
			.model(model)
			.temperature(0.7)
			.messages(List.of(
				new ChatGptRequest.Message("user", prompt)
			))
			.build();

		ChatGptResponse response = getChatGptResponse(request);

		return response.getChoices().get(0).getMessage().getContent();
	}

}