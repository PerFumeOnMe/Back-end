package PerfumeOnMe.spring.external.fastapi;

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
import PerfumeOnMe.spring.external.fastapi.dto.FastApiPbtiRecommendResponse;
import PerfumeOnMe.spring.external.fastapi.dto.FastApiRecommendRequest;
import PerfumeOnMe.spring.external.fastapi.dto.FastApiRecommendResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FastApiClient {

	private final RestTemplate restTemplate;

	@Value("${external.fastapi.recommend-url}")
	private String fastApiRecommendUrl;

	@Value("${external.fastapi.pbti-recommend-url}")
	private String pbtiRecommendUrl;

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

	public FastApiPbtiRecommendResponse getFullPbtiResult(FastApiRecommendRequest.PbtiRequest request) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<FastApiRecommendRequest.PbtiRequest> entity = new HttpEntity<>(request, headers);

			ResponseEntity<FastApiPbtiRecommendResponse> response =
				restTemplate.exchange(pbtiRecommendUrl, HttpMethod.POST, entity, FastApiPbtiRecommendResponse.class);

			return response.getBody();

		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.FASTAPI_COMMUNICATION_ERROR);
		}
	}

}
