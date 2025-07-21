package PerfumeOnMe.spring.service.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
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
			throw new GeneralException(ErrorStatus.FASTAPI_COMMUNICATION_ERROR);
		}
	}
}
