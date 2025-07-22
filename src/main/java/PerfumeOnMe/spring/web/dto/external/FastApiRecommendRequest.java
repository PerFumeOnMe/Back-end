package PerfumeOnMe.spring.web.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FastApiRecommendRequest {
	private String ambience;
	private String style;
	private String gender;
	private String season;
	private String personality;
}