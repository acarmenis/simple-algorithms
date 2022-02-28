package com.zinkworks.exceptions;

import com.zinkworks.domain.error.types.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * ErrorLog is used to log the errors in the same manner always.
 */
@Slf4j
public class ErrorLog {
    /**
     *
     * @param isError a boolean value
     * @param errorType an enum type describing the error
     * @param status http status
     */
    public static void logTheError(boolean isError, ErrorType errorType, HttpStatus status){
        // if there is an error
        if(isError){
            // creates a business exception, sets the appropriate error message and status
            BusinessException businessException = new BusinessException(errorType, status);
            // logs the error to console for debugging purposes
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage());
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }
    }

    /**
     *
     * @param isError a boolean value
     * @param errorType an enum type describing the error
     * @param status http status
     * @param extraInfo extra information if so for even detailed error message
     */

    public static void logExtraInfoError(boolean isError, ErrorType errorType, HttpStatus status, String extraInfo){
        // if there is an error
        if(isError){
            // creates a business exception, sets the appropriate error message and status
            BusinessException businessException = new BusinessException(errorType, status);
            // logs the error to console for debugging purposes
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage().concat("\t").concat(extraInfo));
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }

    }
}
