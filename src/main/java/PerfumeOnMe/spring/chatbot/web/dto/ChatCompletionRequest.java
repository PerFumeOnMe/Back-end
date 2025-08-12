package PerfumeOnMe.spring.chatbot.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatCompletionRequest {
	private String model; // ex: "gpt-3.5-turbo"
	private List<ChatCompletionMessage> messages;
}