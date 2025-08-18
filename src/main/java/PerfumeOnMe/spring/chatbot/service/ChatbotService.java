package PerfumeOnMe.spring.chatbot.service;

import PerfumeOnMe.spring.chatbot.web.dto.ChatBotRequestDTO;
import PerfumeOnMe.spring.chatbot.web.dto.ChatBotResponseDTO;
import reactor.core.publisher.Mono;

public interface ChatbotService {
	// 대화 이력 조회
	ChatBotResponseDTO.ChatBotFinalResponse getChatHistory(Long userId, ChatBotRequestDTO.ChatBotPagingRequest request);

	// 챗봇과 질의 응답
	Mono<String> ask(Long userId, ChatBotRequestDTO.ChatBotQARequest request);
}
