package com.zinkworks.domain.dto.error;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * ErrorResponse a custom error response for error descriptions
 */
@Getter
@Setter
public class ErrorResponse {

    // customizing timestamp serialization format
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    // error code
    private int code;
    // error status
    private String status;
    // error message
    private String message;
    // error stack trace
    private String stackTrace;
    // data
    private Object data;

    /**
     *
     */
    public ErrorResponse() {
        timestamp = new Date();
    }

    /**
     *
     * @param httpStatus
     * @param message
     */
    public ErrorResponse(HttpStatus httpStatus,String message ) {
        this();
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    /**
     *
     * @param httpStatus
     * @param message
     * @param stackTrace
     */
    public ErrorResponse(HttpStatus httpStatus, String message, String stackTrace ) {
        this( httpStatus, message  );
        this.stackTrace = stackTrace;
    }

    /**
     *
     * @param httpStatus
     * @param message
     * @param stackTrace
     * @param data
     */
    public ErrorResponse(HttpStatus httpStatus, String message, String stackTrace, Object data ) {
        this(httpStatus, message, stackTrace );
        this.data = data;
    }
}
