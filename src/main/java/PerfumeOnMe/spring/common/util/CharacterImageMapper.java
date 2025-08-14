package PerfumeOnMe.spring.common.util;

import java.util.Map;

import PerfumeOnMe.spring.common.enums.Ambience;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 분위기별 감성 캐릭터 이미지 URL 매핑 유틸리티
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CharacterImageMapper {

	private static final String BASE_S3_URL = "https://umc-perfume-bucket.s3.ap-northeast-1.amazonaws.com/image-keyword/characters/";
	private static final String DEFAULT_CHARACTER_IMAGE = BASE_S3_URL + "default.png";

	/**
	 * 분위기별 캐릭터 이미지 매핑
	 */
	private static final Map<Ambience, String> CHARACTER_IMAGE_MAP = Map.of(
		Ambience.SOPHISTICATED, BASE_S3_URL + "sophisticated.png",    // 세련된
		Ambience.CUTE, BASE_S3_URL + "cute.png",                      // 귀여운
		Ambience.CALM, BASE_S3_URL + "calm.png",                      // 차분한
		Ambience.MATURE, BASE_S3_URL + "mature.png",                  // 성숙한
		Ambience.LOVELY, BASE_S3_URL + "lovely.png",                  // 러블리한
		Ambience.ELEGANT, BASE_S3_URL + "elegant.png",                // 시크한
		Ambience.FRESH, BASE_S3_URL + "fresh.png",                    // 신비로운
		Ambience.BRIGHT, BASE_S3_URL + "bright.png",                  // 밝은
		Ambience.LIVELY, BASE_S3_URL + "lively.png",                  // 몽환적인
		Ambience.GRACEFUL, BASE_S3_URL + "graceful.png"               // 우아한
	);

	/**
	 * 분위기에 따른 감성 캐릭터 이미지 URL 반환
	 * @param ambience 분위기 Enum
	 * @return 해당 분위기의 캐릭터 이미지 URL
	 */
	public static String getCharacterImageUrl(Ambience ambience) {
		return CHARACTER_IMAGE_MAP.getOrDefault(ambience, DEFAULT_CHARACTER_IMAGE);
	}

	/**
	 * 분위기 이름(displayName)으로 감성 캐릭터 이미지 URL 반환
	 * @param ambienceDisplayName 분위기 이름 (예: "세련된", "귀여운")
	 * @return 해당 분위기의 캐릭터 이미지 URL
	 */
	public static String getCharacterImageUrl(String ambienceDisplayName) {
		try {
			Ambience ambience = Ambience.fromDisplayName(ambienceDisplayName);
			return getCharacterImageUrl(ambience);
		} catch (IllegalArgumentException e) {
			return DEFAULT_CHARACTER_IMAGE;
		}
	}
}