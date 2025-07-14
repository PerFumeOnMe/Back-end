package PerfumeOnMe.spring.config.security.oauth.util;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.domain.enums.Social;

/*
String으로 받아온 provider(ex. kakao)를 Social Enum으로 반환
 */
public class OAuthProviderResolver {

	public static Social resolve(String provider) {
		try {
			return Social.valueOf(provider.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new GeneralException(ErrorStatus.UNSUPPORTED_SOCIAL);
		}
	}

}
