package PerfumeOnMe.spring.s3file.web.dto;

import lombok.Getter;

public class s3RequestDTO {
	@Getter
	public static class PresignedUrlRequestDTO {
		private String fileName; // e.g. "profile.png"
	}
}
