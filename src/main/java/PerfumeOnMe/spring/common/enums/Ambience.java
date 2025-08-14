package PerfumeOnMe.spring.common.enums;

import lombok.Getter;

@Getter
public enum Ambience {
	SOPHISTICATED("세련된"),
	CUTE("귀여운"),
	CALM("차분한"),
	MATURE("성숙한"),
	LOVELY("러블리한"),
	ELEGANT("시크한"),
	FRESH("신비로운"),
	BRIGHT("밝은"),
	LIVELY("몽환적인"),
	GRACEFUL("우아한");

	private final String displayName;

	Ambience(String displayName) {
		this.displayName = displayName;
	}

	public static Ambience fromDisplayName(String displayName) {
		for (Ambience value : Ambience.values()) {
			if (value.getDisplayName().equals(displayName)) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid displayName for Ambience: " + displayName);
	}
}
