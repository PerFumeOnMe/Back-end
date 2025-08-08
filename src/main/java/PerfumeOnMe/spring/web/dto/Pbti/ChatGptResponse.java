package PerfumeOnMe.spring.web.dto.Pbti;

import java.util.List;

import lombok.Data;

@Data
public class ChatGptResponse {

	private List<Choice> choices;

	@Data
	public static class Choice {
		private int index;
		private Message message;
	}

	@Data
	public static class Message {
		private String role;
		private String content;
	}
}
