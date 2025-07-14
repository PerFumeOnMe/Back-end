package PerfumeOnMe.spring.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.service.chatbot.ChatbotService;
import PerfumeOnMe.spring.web.dto.chatbot.ChatBotRequestDTO;
import PerfumeOnMe.spring.web.dto.chatbot.ChatBotResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatbot")
@Tag(name = "Chatbot", description = "챗봇 CRUD API")
public class ChatbotController {
	private final ChatbotService chatbotService;

	// 로그인한 사용자가 챗봇에게 질문을 보내고,
	// OpenAI 로부터 받은 응답을 클라이언트에게 반환하는 API
	@PostMapping
	@Operation(
		summary = "챗봇 질의 응답",
		description = "챗봇에게 질문을 하고 OpenAI 로부터 받은 응답을 반환하는 API 입니다."
	)
	@Parameters({
		@Parameter(name = "message", description = "사용자가 질문할 내용"),
	})
	public Mono<ResponseEntity<ApiResponse<String>>> ask(
		@RequestBody ChatBotRequestDTO.ChatBotQARequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		return chatbotService.ask(userDetails.getUserId(), request)
			.map(answer -> ResponseEntity.ok(ApiResponse.onSuccess(answer)));
	}

	// 대화 이력을 반환하는 API
	@GetMapping("/history")
	@Operation(
		summary = "챗봇 대화 이력 조회",
		description = "챗봇 대화 이력을 조회하는 API 입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChatBotResponseDTO.ChatBotQA.class))),
		}
	)
	public ResponseEntity<ApiResponse<ChatBotResponseDTO.ChatBotFinalResponse>> getHistory(
		@Valid @ModelAttribute ChatBotRequestDTO.ChatBotPagingRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		ChatBotResponseDTO.ChatBotFinalResponse history = chatbotService.getChatHistory(userDetails.getUserId(),
			request);
		return ResponseEntity.ok(ApiResponse.onSuccess(history));
	}
}
