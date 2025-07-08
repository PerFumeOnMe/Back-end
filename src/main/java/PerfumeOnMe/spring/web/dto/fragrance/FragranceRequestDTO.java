package PerfumeOnMe.spring.web.dto.fragrance;

import PerfumeOnMe.spring.validation.annotation.ValidKeyword;
import lombok.Getter;
import lombok.Setter;

public class FragranceRequestDTO {
	@Getter
	@Setter
	public static class FragranceSearchRequest {

		@ValidKeyword
		private String keyword;

		private int page;

		private Integer size = 12; // default
	}
}
