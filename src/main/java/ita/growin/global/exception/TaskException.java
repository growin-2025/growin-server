package ita.growin.global.exception;

import ita.growin.global.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class TaskException extends RuntimeException {

    private final ErrorCode errorCode;

    public TaskException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static EventException of(ErrorCode errorCode) {
        return new EventException(errorCode);
    }
}
