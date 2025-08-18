package PerfumeOnMe.spring.common.enums;

import lombok.Getter;

@Getter
public enum Personality {
	QUIET("조용한"),
	LOGICAL("논리적인"),
	STRONG("개성강한"),
	CHARISMATIC("카리스마 있는"),
	CAUTIOUS("신중한"),
	LIVELY("활발한"),
	WARM("따뜻한"),
	EMOTIONAL("감성적인"),
	FRIENDLY("친근한"),
	PASSIONATE("쾌활한");

	private final String displayName;

	Personality(String displayName) {
		this.displayName = displayName;
	}

	public static Personality fromDisplayName(String displayName) {
		for (Personality p : Personality.values()) {
			if (p.getDisplayName().equals(displayName)) {
				return p;
			}
		}
		throw new IllegalArgumentException("Invalid displayName for Personality: " + displayName);
	}
}
