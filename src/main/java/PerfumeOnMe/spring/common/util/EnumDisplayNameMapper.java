package PerfumeOnMe.spring.common.util;

import java.util.List;

import PerfumeOnMe.spring.common.enums.Ambience;
import PerfumeOnMe.spring.common.enums.Gender;
import PerfumeOnMe.spring.common.enums.Personality;
import PerfumeOnMe.spring.common.enums.Season;
import PerfumeOnMe.spring.common.enums.Style;

public class EnumDisplayNameMapper {

	public static Ambience toAmbience(List<String> keywords) {
		return Ambience.fromDisplayName(keywords.get(0));
	}

	public static Style toStyle(List<String> keywords) {
		return Style.fromDisplayName(keywords.get(1));
	}

	public static Season toSeason(List<String> keywords) {
		return Season.fromDisplayName(keywords.get(2));
	}

	public static Personality toPersonality(List<String> keywords) {
		return Personality.fromDisplayName(keywords.get(3));
	}

	public static Gender toGender(List<String> keywords) {
		return Gender.fromDisplayName(keywords.get(4));
	}
}
