package PerfumeOnMe.spring.domain.enums;

import java.util.Arrays;

public enum Brand {
	LOIVIE,
	DIPTYQUE,
	JOMALONE,
	MAISON_MARGIELA,
	FREDERIC_MALLE;

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
