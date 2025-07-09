package PerfumeOnMe.spring.domain.enums;

import lombok.Getter;

@Getter
public enum Character {
	QUIET("조용한"),
	LOGICAL("논리적인"),
	STRONG("개성 강한"),
	CHARISMATIC("카리스마 있는"),
	CAUTIOUS("신중한"),
	LIVELY("활발한"),
	WARM("따뜻한"),
	EMOTIONAL("감성적인"),
	FRIENDLY("친근한"),
	PASSIONATE("쾌활한");

	private final String displayName;

	Character(String displayName) {
		this.displayName = displayName;
	}
}
