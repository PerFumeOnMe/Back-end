package PerfumeOnMe.spring.security.oauth.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import PerfumeOnMe.spring.common.enums.Social;

/*
Social과 OAuthService 구현체를 맵으로 저장
-> Social에 맞는 OAuthService 구현체를 꺼내는 용도
 */
@Component
public class OAuthServiceFactory {

	private final Map<Social, OAuthService> serviceMap;

	public OAuthServiceFactory(List<OAuthService> serviceList) {
		this.serviceMap = serviceList.stream()
			.collect(Collectors.toMap(OAuthService::getProvider, Function.identity()));
	}

	public OAuthService getOAuthService(Social social) {
		return serviceMap.get(social);
	}
}
