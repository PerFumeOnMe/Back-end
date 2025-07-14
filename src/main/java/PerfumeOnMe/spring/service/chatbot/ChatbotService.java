package PerfumeOnMe.spring.service.chatbot;

import PerfumeOnMe.spring.web.dto.chatbot.ChatBotRequestDTO;
import PerfumeOnMe.spring.web.dto.chatbot.ChatBotResponseDTO;
import reactor.core.publisher.Mono;

public interface ChatbotService {
	// 대화 이력 조회
	ChatBotResponseDTO.ChatBotFinalResponse getChatHistory(Long userId, ChatBotRequestDTO.ChatBotPagingRequest request);

	// 챗봇과 질의 응답
	Mono<String> ask(Long userId, ChatBotRequestDTO.ChatBotQARequest request);
}
