package PerfumeOnMe.spring.security.oauth.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoResponseDTO {

	// 인가 코드로 받급받는 카카오 토큰
	@Getter
	@NoArgsConstructor
	public static class Token {
		private String token_type;
		private String access_token;
		private String id_token;
		private Integer expires_in;
		private String refresh_token;
		private Integer refresh_token_expires_in;
		private String scope;
	}

	// 카카오 토큰으로 가져온 사용자 정보
	@Getter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class UserInfo {
		private Long id;
		private LocalDateTime connected_at;
		private KakaoAccount kakao_account;

		@Getter
		@NoArgsConstructor
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class KakaoAccount {
			private Boolean profile_needs_agreement;
			private Boolean profile_nickname_needs_agreement;
			private Boolean profile_image_needs_agreement;
			private Profile profile;
			private Boolean name_needs_agreement;
			private String name;
			private Boolean email_needs_agreement;
			private Boolean is_email_valid;
			private Boolean is_email_verified;
			private String email;

			@Getter
			@NoArgsConstructor
			@JsonIgnoreProperties(ignoreUnknown = true)
			public static class Profile {
				private String nickname;
				private String thumbnail_image_url;
				private String profile_image_url;
				private Boolean is_default_image;
				private Boolean is_default_nickname;
			}
		}
	}
}
