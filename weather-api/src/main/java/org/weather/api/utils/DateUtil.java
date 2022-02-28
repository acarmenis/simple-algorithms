package org.weather.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtil
 */
public class DateUtil {
    /**
     * formats and prints current time
     */
    public static void printCurrentTime(){
        try {
              // gets the current date, formats it by certain format and just prints it out.
              // this method, is used in the task requests and calls to print the point the current time
              // and to give visible time evidence in the subsequent calls
              System.out.println("Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
              // exceptions are being thrown in the event of any wrong date formatting.
        } catch(NullPointerException | IllegalArgumentException e){
             if(e instanceof NullPointerException){
                 throw new NullPointerException("The given pattern is null");
             }
            throw new IllegalArgumentException("The given pattern is invalid");
        }
    }
}
