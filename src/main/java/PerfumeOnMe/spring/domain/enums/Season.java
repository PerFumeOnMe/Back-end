package PerfumeOnMe.spring.domain.enums;

import lombok.Getter;

@Getter
public enum Season {
	SPRING("봄"),
	SUMMER("여름"),
	AUTUMN("가을"),
	WINTER("겨울");

	private final String displayName;

	Season(String displayName) {
		this.displayName = displayName;
	}
}
