package PerfumeOnMe.spring.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**이미지 키워드 관련 설정 프로퍼티*/
@Component
@ConfigurationProperties(prefix = "app.image-keyword")
@Getter
@Setter
public class ImageKeywordProperties {

	/**FastAPI 연동 설정*/
	private final FastApi fastApi = new FastApi();

	/**감성 캐릭터 이미지 기본 S3 경로*/
	private String characterImageBasePath = "https://umc-perfume-bucket.s3.ap-northeast-1.amazonaws.com/image-keyword/characters/";

	/**Redis 캐시 TTL (분 단위)*/
	private int cacheTimeoutMinutes = 5;

	@Getter
	@Setter
	public static class FastApi {
		/**연결 타임아웃 (밀리초)*/
		private int connectTimeout = 5000;

		/**읽기 타임아웃 (밀리초)*/
		private int readTimeout = 10000;

		/**재시도 최대 횟수*/
		private int maxRetries = 3;

		/**재시도 지연 시간 (밀리초)*/
		private int retryDelay = 1000;
	}
}