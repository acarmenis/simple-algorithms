package com.zinkworks.exceptions;

import com.zinkworks.domain.error.types.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * BusinessException, the custom application exception used for debugging application errors
 */
@Getter
public class BusinessException extends RuntimeException {

    // serialization UID
    private static final long serialVersionUID = 1L;

    // enum ErrorType
    private final ErrorType errorType;
    // httpStatus
    private final HttpStatus httpStatus;

    /**
     *
     * @param errorType enum ErrorType
     * @param httpStatus HttpStatus
     */
    public BusinessException(ErrorType errorType, HttpStatus httpStatus) {
        this.errorType = errorType;
        this.httpStatus = httpStatus;
    }

    /**
     *
     * @param cause
     * @param errorType enum ErrorType
     * @param httpStatus HttpStatus
     */
    public BusinessException(Throwable cause, ErrorType errorType, HttpStatus httpStatus) {
        super(cause);
        this.errorType = errorType;
        this.httpStatus = httpStatus;
    }

    /**
     *
     * @param message the message error
     * @param cause the cause
     * @param enableSuppression  true/false
     * @param writableStackTrace eStackTrace
     * @param errorType enum ErrorType
     * @param httpStatus HttpStatus
     */
    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorType errorType, HttpStatus httpStatus) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorType = errorType;
        this.httpStatus = httpStatus;
    }
}
