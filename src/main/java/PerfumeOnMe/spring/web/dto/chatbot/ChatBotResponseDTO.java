package PerfumeOnMe.spring.web.dto.chatbot;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatBotResponseDTO {

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ChatBotQA {
		private String userMessage;
		private String botResponse;
		private LocalDateTime createdAt;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ChatBotFinalResponse {
		private List<ChatBotQA> content;
		private boolean hasNext;
	}

}
