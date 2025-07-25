package PerfumeOnMe.spring.domain.enums;

import lombok.Getter;

@Getter
public enum Style {
	UNIQUE("유니크한"),
	STREET("스트릿한"),
	ROMANTIC("로맨틱한"),
	HIPHOP("힙한"),
	MODERN("모던한"),
	CLASSIC("클래식한"),
	VINTAGE("빈티지한"),
	CASUAL("캐주얼한"),
	MINIMAL("미니멀한"),
	RETRO("레트로한");

	private final String displayName;

	Style(String displayName) {
		this.displayName = displayName;
	}

	public static Style fromDisplayName(String displayName) {
		for (Style style : Style.values()) {
			if (style.getDisplayName().equals(displayName)) {
				return style;
			}
		}
		throw new IllegalArgumentException("Invalid displayName for Style: " + displayName);
	}
}
