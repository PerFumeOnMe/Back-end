package PerfumeOnMe.spring.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FragranceGender {
	MALE("남성용"), FEMALE("여성용"), NEUTRAL("남녀불문");

	private final String koName;     // 한국어 변환
}
