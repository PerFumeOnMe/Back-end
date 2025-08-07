package PerfumeOnMe.spring.domain.enums;

import lombok.Getter;

@Getter
public enum Gender {
	FEMININE("여성적인"),
	MASCULINE("남성적인"),
	NEUTRAL("중성적인");

	private final String displayName;

	Gender(String displayName) {
		this.displayName = displayName;
	}

	public static Gender fromDisplayName(String displayName) {
		for (Gender g : Gender.values()) {
			if (g.getDisplayName().equals(displayName)) {
				return g;
			}
		}
		throw new IllegalArgumentException("Invalid displayName for Gender: " + displayName);
	}
}
