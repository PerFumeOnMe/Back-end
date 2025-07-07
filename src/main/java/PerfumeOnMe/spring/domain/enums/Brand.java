package PerfumeOnMe.spring.domain.enums;

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
}
