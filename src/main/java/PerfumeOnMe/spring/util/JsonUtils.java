package PerfumeOnMe.spring.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// JSON 직렬화 메서드
public class JsonUtils {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 직렬화 실패", e);
		}
	}
}
