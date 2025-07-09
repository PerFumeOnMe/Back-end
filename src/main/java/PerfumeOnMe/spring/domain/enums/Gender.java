package PerfumeOnMe.spring.domain.enums;

import lombok.Getter;

@Getter
public enum Gender {
	FEMININE("여성스러운"),
	MASCULINE("남성적인"),
	NEUTRAL("중성적인");

	private final String displayName;

	Gender(String displayName) {
		this.displayName = displayName;
	}
}
