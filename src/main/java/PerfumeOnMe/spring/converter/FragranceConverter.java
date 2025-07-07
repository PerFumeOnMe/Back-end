package PerfumeOnMe.spring.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.domain.mapping.FragranceBaseNote;
import PerfumeOnMe.spring.domain.mapping.FragranceMiddleNote;
import PerfumeOnMe.spring.domain.mapping.FragrancePrice;
import PerfumeOnMe.spring.domain.mapping.FragranceTopNote;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceResponseDTO;

@Component
public class FragranceConverter {

	public FragranceResponseDTO.FragranceDetailResult toDetailDto(Fragrance fragrance) {
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
				.map(fl -> fl.getLocation().getName())
				.collect(Collectors.toList()))
			.seasons(fragrance.getFragranceSeasonList().stream()
				.map(fs -> fs.getSeason().getName())
				.collect(Collectors.toList()))
			.homePageUrl(fragrance.getHomePageURL())
			.build();
	}

	// ml 당 가격 추출
	private List<FragranceResponseDTO.FragranceDetailResult.PriceDto> toPriceDtoList(
		List<FragrancePrice> fragrancePrices) {
		return fragrancePrices.stream()
			.map(fp -> FragranceResponseDTO.FragranceDetailResult.PriceDto.builder()
				.mlcount(fp.getPrice().getMlCount())
				.price(fp.getPrice().getPrice())
				.build())
			.collect(Collectors.toList());
	}

	// 탑 노트 추출
	private List<String> extractTopNotes(List<? extends FragranceTopNote> fragranceNotes) {
		return fragranceNotes.stream()
			.map(noteMapping -> noteMapping.getNote().getName())
			.collect(Collectors.toList());
	}

	// 미들 노트 추출
	private List<String> extractMiddleNotes(List<? extends FragranceMiddleNote> fragranceNotes) {
		return fragranceNotes.stream()
			.map(noteMapping -> noteMapping.getNote().getName())
			.collect(Collectors.toList());
	}

	// 베이스 노트 추출
	private List<String> extractBaseNotes(List<? extends FragranceBaseNote> fragranceNotes) {
		return fragranceNotes.stream()
			.map(noteMapping -> noteMapping.getNote().getName())
			.collect(Collectors.toList());
	}

	// 해당 노트, 노트 키워드, 노트 설명 저장.
	private FragranceResponseDTO.FragranceDetailResult.NoteDto.NoteSection toNoteSection(String keyword,
		String description, List<String> ingredients) {
		return FragranceResponseDTO.FragranceDetailResult.NoteDto.NoteSection.builder()
			.keywords(keyword)
			.description(description)
			.ingredients(ingredients)
			.build();
	}
}



