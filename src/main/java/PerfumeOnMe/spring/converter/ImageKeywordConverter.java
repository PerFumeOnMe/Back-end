package PerfumeOnMe.spring.converter;

import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.domain.ImageKeyword;
import PerfumeOnMe.spring.domain.ImageKeywordDescription;
import PerfumeOnMe.spring.domain.enums.KeywordCategory;
import PerfumeOnMe.spring.repository.imagekeyworddescription.ImageKeywordDescriptionRepository;
import PerfumeOnMe.spring.web.dto.imagekeyword.ImageKeywordResponseDTO;

public class ImageKeywordConverter {

	public static List<ImageKeywordResponseDTO.ImageKeywordListResponseDTO> toImageKeywordListResponse(
		List<ImageKeyword> imageKeywords) {
		return imageKeywords.stream()
			.map(imageKeyword -> ImageKeywordResponseDTO.ImageKeywordListResponseDTO.builder()
				.imageKeywordId(imageKeyword.getId())
				.savedName(imageKeyword.getSavedName())
				.createdAt(imageKeyword.getCreatedAt())
				.build())
			.toList();
	}

	public static ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO toImageKeywordDetailResponse(
		ImageKeyword keyword, ImageKeywordDescriptionRepository descriptionRepo
	) {
		List<String> keywords = List.of(
			keyword.getAmbience().getDisplayName(),
			keyword.getStyle().getDisplayName(),
			keyword.getSeason().getDisplayName(),
			keyword.getPersonality().getDisplayName(),
			keyword.getGender().getDisplayName()
		);

		List<String> descriptions = Stream.of(
				new EnumWithCategory(keyword.getAmbience().name(), KeywordCategory.AMBIENCE),
				new EnumWithCategory(keyword.getStyle().name(), KeywordCategory.STYLE),
				new EnumWithCategory(keyword.getSeason().name(), KeywordCategory.SEASON),
				new EnumWithCategory(keyword.getPersonality().name(), KeywordCategory.PERSONALITY),
				new EnumWithCategory(keyword.getGender().name(), KeywordCategory.GENDER)
			)
			.map(pair -> descriptionRepo.findByKeywordAndCategory(pair.keyword, pair.category)
				.map(ImageKeywordDescription::getDescription).orElse(""))
			.toList();

		List<ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO.FragranceRecommendation> recommendations =
			parseFragranceJson(keyword.getRecommendedFragranceJson());

		return ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO.builder()
			.savedName(keyword.getSavedName())
			.keywords(keywords)
			.descriptions(String.join(" ", descriptions))
			.scenario(keyword.getScenario())
			.characterImageUrl(keyword.getImageUrl())
			.recommendations(recommendations)
			.build();
	}

	private static List<ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO.FragranceRecommendation> parseFragranceJson(
		String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json,
				new TypeReference<List<ImageKeywordResponseDTO.ImageKeywordDetailResponseDTO.FragranceRecommendation>>() {
				});
		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}
	}

	private record EnumWithCategory(String keyword, KeywordCategory category) {
	}
}
