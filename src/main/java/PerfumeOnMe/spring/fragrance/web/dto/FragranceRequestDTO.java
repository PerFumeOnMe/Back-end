package PerfumeOnMe.spring.fragrance.web.dto;

import PerfumeOnMe.spring.common.validation.annotation.ValidPage;
import PerfumeOnMe.spring.common.validation.annotation.ValidSize;
import PerfumeOnMe.spring.fragrance.validation.annotation.ValidKeyword;
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

	// 향수 전체 리스트 요청 DTO
	@Getter
	@Setter
	public static class FragranceAllRequest {
		@ValidPage
		private int page;

		@ValidSize
		private int size;
	}

}
