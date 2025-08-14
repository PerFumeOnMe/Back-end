package PerfumeOnMe.spring.chatbot.web.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import PerfumeOnMe.spring.apiPayload.ApiResponse;
import PerfumeOnMe.spring.chatbot.web.dto.ChatBotRequestDTO;
import PerfumeOnMe.spring.chatbot.web.dto.ChatBotResponseDTO;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Tag(name = "Chatbot", description = "챗봇 CRUD API")
public interface ChatbotControllerDocs {

	@Operation(
		summary = "챗봇 질의 응답",
		description = "챗봇에게 질문을 하고 OpenAI 로부터 받은 응답을 반환하는 API 입니다."
	)
	Mono<ResponseEntity<ApiResponse<String>>> ask(
		@RequestBody ChatBotRequestDTO.ChatBotQARequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(
		summary = "챗봇 대화 이력 조회",
		description = "챗봇 대화 이력을 조회하는 API 입니다.",
		responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChatBotResponseDTO.ChatBotQA.class))),
		}
	)
	ResponseEntity<ApiResponse<ChatBotResponseDTO.ChatBotFinalResponse>> getHistory(
		@Valid @ModelAttribute ChatBotRequestDTO.ChatBotPagingRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails);
}