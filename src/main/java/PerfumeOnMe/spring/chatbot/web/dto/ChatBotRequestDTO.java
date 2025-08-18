package PerfumeOnMe.spring.chatbot.web.dto;

import PerfumeOnMe.spring.common.validation.annotation.ValidPage;
import PerfumeOnMe.spring.common.validation.annotation.ValidSize;
import lombok.Getter;
import lombok.Setter;

public class ChatBotRequestDTO {

	@Getter
	@Setter
	public static class ChatBotQARequest {
		private String message;
	}

	@Getter
	@Setter
	public static class ChatBotPagingRequest {
		@ValidPage
		private int page;

		@ValidSize
		private int size;
	}

	// @ModelAttribute 또는 기본 파라미터 바인딩을 사용하는 DTO 는
	// @Getter, @Setter 모두 있어야 값 주입 + 조회가 가능.
	// @Setter 가 없으면 값은 주입되지 않고, getPage() or getSize() 는 기본값을 반환.

}
