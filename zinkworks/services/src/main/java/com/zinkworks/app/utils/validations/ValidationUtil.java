package com.zinkworks.app.utils.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ValidationUtil uses to validate the clients request for moneys and the pins
 */
public class ValidationUtil {

    /**
     *
     * @param pinCode user's pin code
     * @return true/false
     */
    public static boolean isValidPinCode(String pinCode){
        // Regex to check valid pin code.
        String regex = "^[0-9]{4}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the pin code is empty, return false
        if (pinCode == null) {
            return false;
        }

        // Pattern class contains matcher() method to find matching between given pin code and regular expression.
        Matcher m = p.matcher(pinCode);

        // Return if the pin code matched the ReGex
        return m.matches();
    }

    /**
     *
     * @param money the user's money
     * @return true/false
     */
    public static boolean isValidMoney(String money){
        // Regex to check valid money.
        String regex = "^[0-9]{1,4}$";
        // ^      # Assert position at the beginning of the string.
        // $      # Assert position at the end of the string.
        //[0-9]   # Match one letter from 0 to 9
        //{1,4}   # between 1 and 4 times.

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the money is empty, return false
        if (money == null) {
            return false;
        }

        // Pattern class contains matcher() method to find matching between given pin code and regular expression.
        Matcher m = p.matcher(money);

        // Return if the pin code matched the ReGex
        return m.matches();
    }


}
