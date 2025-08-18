package PerfumeOnMe.spring.apiPayload.exception;

import PerfumeOnMe.spring.apiPayload.code.BaseErrorCode;
import PerfumeOnMe.spring.apiPayload.code.ErrorReasonDTO;
import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

	private BaseErrorCode code;

	public GeneralException(BaseErrorCode code, String message) {
		super(message); // 로그에 메시지가 나오게 됨
		this.code = code;
	}

	public ErrorStatus getErrorStatus() {
		if (this.code instanceof ErrorStatus) {
			return (ErrorStatus)this.code;
		}
		return null;
	}

	public ErrorReasonDTO getErrorReason() {
		return this.code.getReason();
	}

	public ErrorReasonDTO getErrorReasonHttpStatus() {
		return this.code.getReasonHttpStatus();
	}
}
