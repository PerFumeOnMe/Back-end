package PerfumeOnMe.spring.converter;

import java.util.List;
import java.util.stream.Collectors;

import PerfumeOnMe.spring.domain.ChatMessage;
import PerfumeOnMe.spring.web.dto.chatbot.ChatBotResponseDTO;

public class ChatConverter {
	// 챗봇과 사용자의 대화 단건 저장
	public static ChatBotResponseDTO.ChatBotQA toDto(ChatMessage cm) {
		return ChatBotResponseDTO.ChatBotQA.builder()
			.userMessage(cm.getUserMessage())
			.botResponse(cm.getBotResponse())
			.createdAt(cm.getCreatedAt())
			.build();
	}

	// toDto 를 통해 반환된 단건 대화들의 리스트를 반환하는 메서드
	public static List<ChatBotResponseDTO.ChatBotQA> toDtoList(List<ChatMessage> list) {
		return list.stream()
			.map(ChatConverter::toDto)
			.collect(Collectors.toList());
	}
}
