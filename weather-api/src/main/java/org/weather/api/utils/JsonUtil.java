package org.weather.api.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * JsonUtil
 */
public class JsonUtil {

    /**
     * The method converts from any textified json string to json object.
     * @param jsonString
     * @return
     */
   public static JSONObject convertFromText(final String jsonString){
       JSONObject json = null;
       // initiate the json parser
       JSONParser parser = new JSONParser();
       try {
              // .. and parses the textified json string
              json = (JSONObject) parser.parse(jsonString);
              // any parse exception, can be caught
       } catch (ParseException e) {
               e.printStackTrace();
       }
       // return the json object
       return json;
   }


}
