package PerfumeOnMe.spring.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Brand {
	LOIVIE("로이비 (LOIVIE)"),
	DIPTYQUE("딥티크 (DIPTYQUE)"),
	JOMALONE("조 말론 (JOMALONE)"),
	MAISON_MARGIELA("메종 마르지엘라 (MAISON MARGIELA)"),
	FREDERIC_MALLE("프레데릭 말 (FREDERIC MALLE)"),
	BYREDO("바이레도 (BYREDO)"),
	TOM_FORD("톰 포드 (TOM FORD)"),
	AESOP("이솝 (AESOP)"),
	YVES_SAINT_LAURENT("입생로랑 (YVES SAINT LAURENT)"),
	LE_LABO("르 라보 (LE LABO)"),
	VERSACE("베르사체 (VERSACE)"),
	MAISON_FRANCIS_KURKDJIAN("메종 프란시스 커정 (MAISON FRANCIS_KURKDJIAN)");

	private final String showBrand;
}
