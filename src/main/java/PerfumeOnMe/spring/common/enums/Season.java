package PerfumeOnMe.spring.common.enums;

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

	public static Season fromDisplayName(String displayName) {
		for (Season season : Season.values()) {
			if (season.getDisplayName().equals(displayName)) {
				return season;
			}
		}
		throw new IllegalArgumentException("Invalid displayName for Season: " + displayName);
	}
}
