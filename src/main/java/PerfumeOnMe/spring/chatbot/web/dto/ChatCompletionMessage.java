package PerfumeOnMe.spring.chatbot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatCompletionMessage {

	// "system"	GPT 의 행동 방식을 지정하는 지침 (초기 설정)
	// "user"	사용자가 입력한 질문, 요청, 대화
	// "assistant"	GPT 가 응답한 내용 (이전 응답들)
	private String role;    // "system" | "user" | "assistant"
	private String content; // 프롬프트, 사용자의 질문. 챗봇 응답
}