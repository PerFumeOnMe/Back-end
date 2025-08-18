package PerfumeOnMe.spring.security.oauth.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.security.oauth.dto.KakaoProperties;
import PerfumeOnMe.spring.security.oauth.dto.KakaoResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
Kakao API로 통신하는 클래스
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoClient {

	private final KakaoProperties kakaoProperties;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	// 인가 코드로 카카오 토큰 요청
	public KakaoResponseDTO.Token requestToken(String code) {

		// HttpEntity 헤더 작성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpEntity 바디 작성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", kakaoProperties.getAuthorizationGrantType());
		params.add("client_id", kakaoProperties.getClientId());
		params.add("redirect_uri", kakaoProperties.getRedirectUri());
		params.add("code", code);
		// params.add("client_secret", kakaoProperties.getClientSecret());

		// HttpEntity 생성
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		// 카카오 토큰 요청 및 반환
		try {
			ResponseEntity<String> response = restTemplate
				.exchange(kakaoProperties.getTokenUri(), HttpMethod.POST, request, String.class);
			KakaoResponseDTO.Token token = objectMapper.readValue(response.getBody(), KakaoResponseDTO.Token.class);
			return token;
		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.PARSE_ERROR);
		}
	}

	// 카카오 토큰으로 사용자 정보 요청
	public KakaoResponseDTO.UserInfo requestUserInfo(String accessToken) {

		// HttpEntity 헤더 작성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("Authorization", "Bearer " + accessToken);

		// HttpEntity 생성
		HttpEntity<String> request = new HttpEntity<>(headers);

		// 사용자 정보 요청 및 반환
		try {
			ResponseEntity<String> response = restTemplate
				.exchange(kakaoProperties.getUserInfoUri(), HttpMethod.GET, request, String.class);
			KakaoResponseDTO.UserInfo userInfo = objectMapper.readValue(response.getBody(),
				KakaoResponseDTO.UserInfo.class);
			return userInfo;
		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.PARSE_ERROR);
		}
	}
}
