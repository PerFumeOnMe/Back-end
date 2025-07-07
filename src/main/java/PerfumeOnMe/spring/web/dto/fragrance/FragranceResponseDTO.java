package PerfumeOnMe.spring.web.dto.fragrance;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FragranceResponseDTO {

	// 향수 상세 페이지 응답 결과
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
}


