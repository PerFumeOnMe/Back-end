package PerfumeOnMe.spring.converter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.domain.Location;
import PerfumeOnMe.spring.domain.Note;
import PerfumeOnMe.spring.domain.Season;
import PerfumeOnMe.spring.domain.mapping.FragranceBaseNote;
import PerfumeOnMe.spring.domain.mapping.FragranceLocation;
import PerfumeOnMe.spring.domain.mapping.FragranceMiddleNote;
import PerfumeOnMe.spring.domain.mapping.FragrancePrice;
import PerfumeOnMe.spring.domain.mapping.FragranceSeason;
import PerfumeOnMe.spring.domain.mapping.FragranceTopNote;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceResponseDTO;

public class FragranceConverter {

	public static FragranceResponseDTO.FragranceDetailResult toDetailDto(Fragrance fragrance) {
		return FragranceResponseDTO.FragranceDetailResult.builder()
			.id(fragrance.getId())
			.brand(fragrance.getBrand().getShowBrand())
			.name(fragrance.getName())
			.priceList(toPriceDtoList(fragrance.getFragrancePriceList()))
			.keyword(fragrance.getKeyword())
			.description(fragrance.getDescription())
			.note(FragranceResponseDTO.FragranceDetailResult.NoteDto.builder()
				.top(toNoteSection(fragrance.getTopNoteKeyword(), fragrance.getTopNoteDescription(),
					extractTopNotes(fragrance.getFragranceTopNoteList())))
				.middle(toNoteSection(fragrance.getMiddleNoteKeyword(), fragrance.getMiddleNoteDescription(),
					extractMiddleNotes(fragrance.getFragranceMiddleNoteList())))
				.base(toNoteSection(fragrance.getBaseNoteKeyword(), fragrance.getBaseNoteDescription(),
					extractBaseNotes(fragrance.getFragranceBaseNoteList())))
				.build())
			.fragranceType(FragranceResponseDTO.FragranceDetailResult.FragranceTypeDto.builder()
				.lastingPower(fragrance.getFragranceType().getLastingPower())
				.diffusionRange(fragrance.getFragranceType().getDiffusionRange())
				.diffusionPower(fragrance.getFragranceType().getDiffusionPower())
				.build())
			.gender(fragrance.getGender().getKoName())
			.locations(fragrance.getFragranceLocationList().stream()
				.map(FragranceLocation::getLocation)
				.filter(Objects::nonNull)
				.map(Location::getName)
				.collect(Collectors.toList()))
			.seasons(fragrance.getFragranceSeasonList().stream()
				.map(FragranceSeason::getSeason)
				.filter(Objects::nonNull)
				.map(Season::getName)
				.collect(Collectors.toList()))
			.homePageUrl(fragrance.getHomePageURL())
			.build();
	}

	// ml 당 가격 추출
	private static List<FragranceResponseDTO.FragranceDetailResult.PriceDto> toPriceDtoList(
		List<FragrancePrice> fragrancePrices) {
		return fragrancePrices.stream()
			.filter(Objects::nonNull)
			.map(FragrancePrice::getPrice)
			.filter(Objects::nonNull)
			.map(price -> FragranceResponseDTO.FragranceDetailResult.PriceDto.builder()
				.mlcount(price.getMlCount())
				.price(price.getPrice())
				.build())
			.collect(Collectors.toList());
	}

	// 탑 노트 추출
	private static List<String> extractTopNotes(List<? extends FragranceTopNote> fragranceNotes) {
		return fragranceNotes.stream()
			.map(FragranceTopNote::getNote)
			.filter(Objects::nonNull)
			.map(Note::getName)
			.collect(Collectors.toList());
	}

	// 미들 노트 추출
	private static List<String> extractMiddleNotes(List<? extends FragranceMiddleNote> fragranceNotes) {
		return fragranceNotes.stream()
			.map(FragranceMiddleNote::getNote)
			.filter(Objects::nonNull)
			.map(Note::getName)
			.collect(Collectors.toList());
	}

	// 베이스 노트 추출
	private static List<String> extractBaseNotes(List<? extends FragranceBaseNote> fragranceNotes) {
		return fragranceNotes.stream()
			.map(FragranceBaseNote::getNote)
			.filter(Objects::nonNull)
			.map(Note::getName)
			.collect(Collectors.toList());
	}

	// 해당 노트, 노트 키워드, 노트 설명 저장
	private static FragranceResponseDTO.FragranceDetailResult.NoteDto.NoteSection toNoteSection(String keyword,
		String description, List<String> ingredients) {
		return FragranceResponseDTO.FragranceDetailResult.NoteDto.NoteSection.builder()
			.keywords(keyword)
			.description(description)
			.ingredients(ingredients)
			.build();
	}
}
