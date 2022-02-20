package com.zinkworks.exceptions;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.zinkworks.domain.dto.error.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ControllerAdvisor, used for catching thrown application errors
 * advices to see first, the highest errors
 * specifies, what packages to listen to
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = {"com.zinkworks.web.controllers",
                                  "com.zinkworks.services",
                                  "com.zinkworks.domain",
                                  "com.zinkworks.persistence" })
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    /**
     *
     * @param ex exception
     * @param request the web request
     * @return ResponseEntity
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleNodataFoundException(BusinessException ex, WebRequest request) {
        // returned a ResponseEntity with customized error response info ErrorResponse
        return new ResponseEntity<>(new ErrorResponse(ex.getHttpStatus(), ex.getErrorType().getMessage()), ex.getHttpStatus() );
    }

    /**
     *
     * @param e exception
     * @return ResponseEntity
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity <ErrorResponse> notFoundException(final NullPointerException e) {
        // returned a ResponseEntity with customized error response info ErrorResponse
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND );
    }

    /**
     *
     * @param ex exception
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // returned a ResponseEntity with customized error response info ErrorResponse
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST );
    }

    /**
     *
     * @param ex Throwable
     * @return ResponseEntity
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleDefaultException(Throwable ex) {
        // returned a ResponseEntity with customized error response info ErrorResponse
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST );
    }

    /**
     *
     * @param ex JsonMappingException
     * @return ResponseEntity
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleJsonMappingException(JsonMappingException ex) {
        // returned a ResponseEntity with customized error response info ErrorResponse
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST );
    }

    /**
     *
     * @param e ArrayIndexOutOfBoundsException
     * @return ResponseEntity
     */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<ErrorResponse> assertionException(final ArrayIndexOutOfBoundsException e) {
        // returned a ResponseEntity with customized error response info ErrorResponse
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND );
    }

    /**
     *
     * @param ex Exception
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // returned a ResponseEntity with customized error response info ErrorResponse
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR );
    }


}
