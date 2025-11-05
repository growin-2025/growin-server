package ita.growin.global.exception;

import ita.growin.global.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class EventException extends RuntimeException {

	private final ErrorCode errorCode;

	public EventException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public static EventException of(ErrorCode errorCode) {
		return new EventException(errorCode);
	}
}

