package PerfumeOnMe.spring.web.dto.fragrance;

import PerfumeOnMe.spring.validation.annotation.ValidKeyword;
import PerfumeOnMe.spring.validation.annotation.ValidPage;
import PerfumeOnMe.spring.validation.annotation.ValidSize;
import lombok.Getter;
import lombok.Setter;

public class FragranceRequestDTO {

	// 향수 검색 요청 DTO
	@Getter
	@Setter
	public static class FragranceSearchRequest {

		@ValidKeyword
		private String keyword;

		@ValidPage
		private int page;

		@ValidSize
		private int size;
	}

	// 향수 필터링 요청 DTO
	@Getter
	@Setter
	public static class FragranceFilterRequest {

		private Long noteCategoryId;

		private String gender;

		private String fragranceType;

		private Long situationId;

		private Long seasonId;

		private Integer priceMin;

		private Integer priceMax;

		@ValidPage
		private Integer page;

		@ValidSize
		private Integer size;

	}

}
