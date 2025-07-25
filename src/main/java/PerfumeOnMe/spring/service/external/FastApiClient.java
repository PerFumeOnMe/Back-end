package PerfumeOnMe.spring.service.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import PerfumeOnMe.spring.web.dto.external.FastApiRecommendRequest;
import PerfumeOnMe.spring.web.dto.external.FastApiRecommendResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FastApiClient {

	private final RestTemplate restTemplate;

	@Value("${external.fastapi.recommend-url}")
	private String fastApiRecommendUrl;

	public FastApiRecommendResponse getFullRecommendation(FastApiRecommendRequest request) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<FastApiRecommendRequest> entity = new HttpEntity<>(request, headers);

			ResponseEntity<FastApiRecommendResponse> response =
				restTemplate.exchange(fastApiRecommendUrl, HttpMethod.POST, entity, FastApiRecommendResponse.class);

			return response.getBody();

		} catch (Exception e) {
			// throw new GeneralException(ErrorStatus.FASTAPI_COMMUNICATION_ERROR);
			return new FastApiRecommendResponse(); //임시조치 : FAST API 서버 없을 때 기본응답반환으로 서버 유지
		}
	}
}
