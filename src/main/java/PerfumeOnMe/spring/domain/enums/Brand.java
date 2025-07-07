package PerfumeOnMe.spring.domain.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Brand {
	LOIVIE("로이비 (LOIVIE)"),
	DIPTYQUE("딥티크 (DIPTYQUE)"),
	JOMALONE("조 말론 (JOMALONE)"),
	MAISON_MARGIELA("메종 마르지엘라 (MAISON MARGIELA)"),
	FREDERIC_MALLE("프레데릭 말 (FREDERIC MALLE)");

	private final String showBrand;

	public static Brand fromString(String input) {
		if (input == null) {
			throw new IllegalArgumentException("브랜드명이 null입니다.");
		}

		// "MAISON MARGIELA" → "MAISON_MARGIELA"
		String normalized = input.trim().toUpperCase().replace(" ", "_");

		return Arrays.stream(values())
			.filter(b -> b.name().equals(normalized))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Unknown brand: " + input));
	}

}
