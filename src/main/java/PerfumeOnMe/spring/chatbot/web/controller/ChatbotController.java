package PerfumeOnMe.spring.chatbot.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.chatbot.service.ChatbotService;
import PerfumeOnMe.spring.chatbot.web.docs.ChatbotControllerDocs;
import PerfumeOnMe.spring.chatbot.web.dto.ChatBotRequestDTO;
import PerfumeOnMe.spring.chatbot.web.dto.ChatBotResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatbotController implements ChatbotControllerDocs {
	private final ChatbotService chatbotService;

	// 로그인한 사용자가 챗봇에게 질문을 보내고,
	// OpenAI 로부터 받은 응답을 클라이언트에게 반환하는 API
	@PostMapping
	public Mono<ResponseEntity<ApiResponse<String>>> ask(
		@RequestBody ChatBotRequestDTO.ChatBotQARequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		return chatbotService.ask(userDetails.getUserId(), request)
			.map(answer -> ResponseEntity.ok(ApiResponse.onSuccess(answer)));
	}

	// 대화 이력을 반환하는 API
	@GetMapping("/history")
	public ResponseEntity<ApiResponse<ChatBotResponseDTO.ChatBotFinalResponse>> getHistory(
		@Valid @ModelAttribute ChatBotRequestDTO.ChatBotPagingRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		ChatBotResponseDTO.ChatBotFinalResponse history = chatbotService.getChatHistory(userDetails.getUserId(),
			request);
		return ResponseEntity.ok(ApiResponse.onSuccess(history));
	}
}
