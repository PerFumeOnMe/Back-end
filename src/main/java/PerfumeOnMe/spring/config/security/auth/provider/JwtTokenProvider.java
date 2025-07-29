package PerfumeOnMe.spring.config.security.auth.provider;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.config.security.auth.dto.JwtProperties;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/*
토큰 추출, 생성, 유효성 검증을 담당하는 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;
	private Key signingKey;

	// 토큰 서명에 쓰이는 key 생성
	@PostConstruct
	private void init() {
		this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
	}

	// 액세스 토큰 생성 - 2시간
	public String createAccessToken(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		String loginId = userDetails.getUsername();
		Long userId = userDetails.getUserId();
		String name = userDetails.getName();

		return Jwts.builder()
			.setSubject(loginId)
			.claim("userId", userId)
			.claim("name", name)
			.setIssuedAt(new Date())
			.setExpiration(new Date(
				System.currentTimeMillis() + jwtProperties.getExpiration().getAccess()))
			.signWith(signingKey)
			.compact();
	}

	// 리프레시 토큰 생성 - 2주
	public String createRefreshToken(Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		String loginId = userDetails.getUsername();
		Long userId = userDetails.getUserId();

		return Jwts.builder()
			.setSubject(loginId)
			.claim("userId", userId)
			.setIssuedAt(new Date())
			.setExpiration(new Date(
				System.currentTimeMillis() + jwtProperties.getExpiration().getRefresh()))
			.signWith(signingKey)
			.compact();
	}

	// 토큰 유효성 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(signingKey)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			throw new GeneralException(ErrorStatus.EXPIRED_TOKEN);
		} catch (MalformedJwtException e) {
			throw new GeneralException(ErrorStatus.MALFORMED_TOKEN);
		} catch (UnsupportedJwtException e) {
			throw new GeneralException(ErrorStatus.UNSUPPORTED_TOKEN);
		} catch (SignatureException e) {
			throw new GeneralException(ErrorStatus.INVALID_SIGNATURE);
		} catch (IllegalArgumentException e) {
			throw new GeneralException(ErrorStatus.TOKEN_NOT_FOUND);
		} catch (JwtException e) {
			throw new GeneralException(ErrorStatus.INVALID_TOKEN);
		}
	}

	// 요청에서 토큰 추출
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	// 토큰에서 Subject 추출
	public String getSubject(String token) {
		validateToken(token);

		return Jwts.parserBuilder()
			.setSigningKey(signingKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	// 토큰에서 Expiration 추출
	public Long getExpiration(String token) {
		validateToken(token);

		return Jwts.parserBuilder()
			.setSigningKey(signingKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getExpiration()
			.getTime();
	}
}
