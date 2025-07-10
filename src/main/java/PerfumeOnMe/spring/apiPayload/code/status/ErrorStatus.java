package PerfumeOnMe.spring.apiPayload.code.status;

import org.springframework.http.HttpStatus;

import PerfumeOnMe.spring.apiPayload.code.BaseErrorCode;
import PerfumeOnMe.spring.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

	//일반적인 에러
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	// 사용자 에러
	LOGIN_ID_DUPLICATE(HttpStatus.BAD_REQUEST, "MEMBER4001", "이미 사용된 아이디입니다."),
	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "MEMBER4002", "비밀번호가 일치하지 않습니다."),
	LOGIN_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4003", "해당 아이디를 가진 사용자가 존재하지 않습니다."),
	LOGIN_PARSING_FAIL(HttpStatus.BAD_REQUEST, "MEMBER4004", "로그인 DTO 변환을 실패했습니다."),
	LOGIN_UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "MEMBER4005", "로그인 중 알 수 없는 오류가 발생했습니다."),

	// 토큰 에러
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4001", "유효하지 않은 토큰입니다."),
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TOKEN4002", "해당 리프레시 토큰이 존재하지 않습니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4003", "만료된 토큰입니다."),
	LOGOUT_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4004", "로그아웃한 액세스 토큰입니다."),
	MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4005", "토큰 구조가 잘못됐습니다."),
	UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4006", "지원하지 않는 토큰 형식입니다."),
	INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "TOKEN4007", "토큰의 서명이 잘못됐습니다."),
	TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "TOKEN4008", "토큰이 없습니다."),

	// 데이터시트 에러
	UNSUPPORTED_BRAND(HttpStatus.BAD_REQUEST, "DATA4001", "지원하지 않는 브랜드입니다."),
	UNSUPPORTED_TYPE(HttpStatus.BAD_REQUEST, "DATA4002", "지원하지 않는 향수타입입니다."),
	PRICE_PARSING_ERROR(HttpStatus.BAD_REQUEST, "DATA4003", "가격 정보를 숫자로 변환할 수 없습니다."),

	// 향수 상세 페이지 에러
	FRAGRANCE_NOT_FOUND(HttpStatus.BAD_REQUEST, "FRAGRANCE4001", "해당 ID에 해당하는 향수를 찾을 수 없습니다."),

	// 향수 즐겨찾기 에러
	ALREADY_FAVORITES_ERROR(HttpStatus.BAD_REQUEST, "FAVORITES4001", "이미 즐겨찾기에 등록한 향수입니다."),
  
	// 향수 필터링 에러
	INVALID_GENDER(HttpStatus.BAD_REQUEST, "FILTER4001", "유효하지 않은 성별입니다."),
	INVALID_FRAGRANCE_TYPE(HttpStatus.BAD_REQUEST, "FILTER4002", "유효하지 않은 향수 타입입니다."),
	INVALID_NOTE_ID(HttpStatus.BAD_REQUEST, "FILTER4003", "유효하지 않은 노트 ID 입니다."),
	INVALID_SEASON_ID(HttpStatus.BAD_REQUEST, "FILTER4004", "유효하지 않은 계절 ID 입니다."),
	INVALID_SITUATION_ID(HttpStatus.BAD_REQUEST, "FILTER4005", "유효하지 않은 장소 ID 입니다."),
	INVALID_PRICE_RANGE(HttpStatus.BAD_REQUEST, "FILTER4006", "가격 범위가 올바르지 않습니다."),

	// 예시,,,
	ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ErrorReasonDTO getReason() {
		return ErrorReasonDTO.builder()
			.message(message)
			.code(code)
			.isSuccess(false)
			.build();
	}

	@Override
	public ErrorReasonDTO getReasonHttpStatus() {
		return ErrorReasonDTO.builder()
			.message(message)
			.code(code)
			.isSuccess(false)
			.httpStatus(httpStatus)
			.build()
			;
	}
}

