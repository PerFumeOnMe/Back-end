package PerfumeOnMe.spring.web.dto.fragrance;

import PerfumeOnMe.spring.validation.annotation.ValidKeyword;
import PerfumeOnMe.spring.validation.annotation.ValidPage;
import PerfumeOnMe.spring.validation.annotation.ValidSize;
import lombok.Getter;
import lombok.Setter;

public class FragranceRequestDTO {
	@Getter
	@Setter
	public static class FragranceSearchRequest {

		@ValidKeyword
		private String keyword;

		@ValidPage
		private int page;

		@ValidSize
		private Integer size;
	}
}
