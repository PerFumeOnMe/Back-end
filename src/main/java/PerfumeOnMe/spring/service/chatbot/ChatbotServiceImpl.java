package PerfumeOnMe.spring.service.chatbot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.converter.ChatConverter;
import PerfumeOnMe.spring.domain.ChatMessage;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.repository.chatbot.ChatMessageRepository;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.web.dto.chatbot.ChatBotRequestDTO;
import PerfumeOnMe.spring.web.dto.chatbot.ChatBotResponseDTO;
import PerfumeOnMe.spring.web.dto.chatbot.ChatCompletionMessage;
import PerfumeOnMe.spring.web.dto.chatbot.ChatCompletionRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatbotServiceImpl implements ChatbotService {
	private final WebClient openAiWebClient; // OpenAI API 를 호출하기 위한 HTTP 클라이언트
	private final PromptLoader promptLoader; // 지정해둔 프롬프트 파일(resources/prompts/expert.txt)을 읽어오는 유틸
	private final ChatMessageRepository chatMessageRepository; // 사용자-챗봇의 대화 이력을 DB에 저장하기 위한 Repository
	private final UserRepository userRepository;

	@Value("${openai.model}")
	private String model; // OpenAI 모델 이름 - gpt-3.5-turbo

	private String systemPrompt; // ← 캐시된 프롬프트

	@PostConstruct
	public void init() {
		this.systemPrompt = promptLoader.loadDefaultPrompt(); // 프롬프트 파일 로딩
	}

	/**
	 * userId: 현재 로그인한 사용자 ID
	 * request: 사용자 질문이 담긴 DTO
	 * Mono<String>: 비동기적으로 OpenAI 응답을 받아서 리턴
	 * */
	@Override
	public Mono<String> ask(Long userId, ChatBotRequestDTO.ChatBotQARequest request) {
		if (request.getMessage() == null) {
			throw new GeneralException(ErrorStatus.REQUIRED_MESSAGES);
		}
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		// 과거 대화 이력 10개 가져오기 (최신순 정렬 → 다시 역순 정렬 필요)
		List<ChatMessage> history = chatMessageRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId);
		Collections.reverse(history); // 오래된 대화부터 시작하도록 정렬

		List<ChatCompletionMessage> messages = new ArrayList<>();
		messages.add(new ChatCompletionMessage("system", systemPrompt));

		for (ChatMessage msg : history) {
			messages.add(new ChatCompletionMessage("user", msg.getUserMessage()));
			messages.add(new ChatCompletionMessage("assistant", msg.getBotResponse()));
		}

		// 현재 사용자의 질문 추가
		messages.add(new ChatCompletionMessage("user", request.getMessage()));

		ChatCompletionRequest openAiRequest = ChatCompletionRequest.builder()
			.model(model) // OpenAI 모델
			.messages(messages)
			.build();

		return openAiWebClient.post()
			.uri("/chat/completions") // OpenAI의 채팅 응답 API 엔드포인트
			.bodyValue(openAiRequest)// 위에서 만든 요청 객체 전송
			.retrieve()
			.onStatus( // 429(Too Many Request) 에러 시 예외처리
				status -> status.value() == 429,
				clientResponse -> clientResponse.bodyToMono(String.class)
					.flatMap(body -> Mono.error(
						new GeneralException(ErrorStatus.OPENAI_RATE_LIMIT_EXCEEDED)
					))
			)
			.bodyToMono(JsonNode.class) // 응답을 JSON 트리로 받음
			.map(json -> json.get("choices").get(0).get("message").get("content").asText())
			.map(response -> {
				// 사용자의 질문과 OpenAI의 응답을 ChatMessage 로 묶어서 DB 저장
				ChatMessage chat = ChatMessage.builder()
					.user(user)
					.userMessage(request.getMessage())
					.botResponse(response)
					.build();
				chatMessageRepository.save(chat); // chatMessageRepository 에 사용자와 챗봇의 대화 이력을 저장
				return response;
			});
	}

	/**
	 * 대화 이력 조회
	 * */
	@Override
	public ChatBotResponseDTO.ChatBotFinalResponse getChatHistory(Long userId,
		ChatBotRequestDTO.ChatBotPagingRequest request) {
		PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(),
			Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<ChatMessage> chats = chatMessageRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);

		List<ChatBotResponseDTO.ChatBotQA> content = ChatConverter.toDtoList(chats.getContent());

		return new ChatBotResponseDTO.ChatBotFinalResponse(content, chats.hasNext());
	}
}
