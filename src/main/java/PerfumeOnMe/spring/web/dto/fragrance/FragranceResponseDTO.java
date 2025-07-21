package PerfumeOnMe.spring.web.dto.fragrance;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FragranceResponseDTO {

	// 향수 상세 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FragranceDetailResult {
		private Long id;
		private String brand;
		private String name;
		private List<PriceDto> priceList;
		private String keyword;
		private String description;
		private NoteDto note;
		private FragranceTypeDto fragranceType;
		private String gender;
		private List<String> locations;
		private List<String> seasons;
		private String homePageUrl;
		private String imageURL;
		private boolean liked;

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class PriceDto {
			private int mlcount;
			private int price;
		}

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class NoteDto {
			private NoteSection top;
			private NoteSection middle;
			private NoteSection base;

			@Getter
			@Builder
			@AllArgsConstructor
			@NoArgsConstructor
			public static class NoteSection {
				private List<String> ingredients;
				private String keywords;
				private String description;
			}
		}

		@Getter
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public static class FragranceTypeDto {
			private String lastingPower;
			private int diffusionRange;
			private String diffusionPower;
		}
	}

	// 향수 검색,필터링 응답 DTO (각 향수 단건)
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FragranceSearchResult {
		private Long id;
		private String brand;
		private String name;
		private Integer minPrice;
		private String imageUrl;
		private boolean liked;
	}

	// 향수 즐겨찾기 등록 응답 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FavoriteResponseDTO {
		private Long fragranceId;
	}

	// 향수 즐겨찾기 등록 취소 DTO
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FavoriteCancelResponseDTO {
		private Long fragranceId;
	}

	// 향수, 검색 필터링 응답 DTO (최종 응답)
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FragranceSearchFinalResult {
		private List<FragranceSearchResult> content;
		private boolean hasNext;
	}

	// 메인페이지 향수 추천(MD's Choice) 목록
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FragranceMdChoiceResult {
		private List<FragranceSearchResult> content;
	}

}


