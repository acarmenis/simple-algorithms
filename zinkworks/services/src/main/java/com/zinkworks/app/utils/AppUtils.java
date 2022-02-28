package com.zinkworks.app.utils;

import com.zinkworks.domain.error.types.ErrorType;
import com.zinkworks.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * AppUtils used to host any tool kit assistance for the application
 * Provides string-to-int conversion
 */
@Slf4j
public class AppUtils {

    /**
     *
     * @param token string token
     * @return its equivalent converted to int number
     */
    public static int convertFromStringToInt(String token){
        // sets the local int number initialized to zero
        int number = 0;
        try{
            // does the conversion
            number = Integer.parseInt(token);
            // the catch error blocl
        } catch (NullPointerException | NumberFormatException e){
            // creates the business error, with an appropriate message and http status
            BusinessException businessException = new BusinessException(ErrorType.MONEY_BAD_FORMAT, HttpStatus.BAD_REQUEST);
            // logs the particular error
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage());
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }
        // return the converted number
        return number;
    }


}
