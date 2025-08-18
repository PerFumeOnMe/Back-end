package PerfumeOnMe.spring.pbti.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatGptRequest {
	private String model;
	private List<Message> messages;
	private double temperature;

	@Data
	@AllArgsConstructor
	public static class Message {
		private String role;    // "user" or "system"
		private String content;
	}
}
