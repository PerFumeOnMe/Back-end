package PerfumeOnMe.spring.web.dto.s3;

import lombok.Getter;

public class s3RequestDTO {
	@Getter
	public static class PresignedUrlRequestDTO {
		private String fileName; // e.g. "profile.png"
	}
}
