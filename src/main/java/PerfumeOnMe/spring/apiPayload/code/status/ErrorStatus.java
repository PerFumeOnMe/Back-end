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
	PASSWORD_CONFIRM_FAIL(HttpStatus.BAD_REQUEST, "MEMBER4002", "비밀번호 확인을 실패했습니다."),

	// 데이터시트 에러
	UNSUPPORTED_BRAND(HttpStatus.BAD_REQUEST, "DATA4001", "지원하지 않는 브랜드입니다."),
	UNSUPPORTED_TYPE(HttpStatus.BAD_REQUEST, "DATA4002", "지원하지 않는 향수타입입니다."),
	PRICE_PARSING_ERROR(HttpStatus.BAD_REQUEST, "DATA4003", "가격 정보를 숫자로 변환할 수 없습니다."),

	// 향수 상세 페이지 에러
	FRAGRANCE_NOT_FOUND(HttpStatus.BAD_REQUEST, "FRAGRANCE4001", "해당 ID에 해당하는 향수를 찾을 수 없습니다."),

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

