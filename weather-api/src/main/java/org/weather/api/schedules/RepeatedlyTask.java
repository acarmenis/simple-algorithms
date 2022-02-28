package org.weather.api.schedules;

import org.json.simple.JSONObject;
import org.weather.api.client.AsynchronousHttpClient;
import org.weather.api.utils.*;
import org.weather.api.weather.WeatherDTO;
import java.io.IOException;
import java.util.*;

/**
 * RepeatedlyTask task
 */
public class RepeatedlyTask {

    /** Timer is the scheduler. */
    Timer timer;

    /** properties: The application properties. */
    final Properties properties;

    /** PathBuilder: build the weather appi's url. */
    final PathBuilder builder;

    /**  Constructor */
    public RepeatedlyTask() {
        //inits the Timer scheduler
        timer = new Timer();
        //inits properties */
        properties = loadAppProperties();
        // builder, builds the sms path/url
        builder = createPath(properties);
        //DELAY_SECONDS, gets initiated from the property value.
        long DELAY_SECONDS = Long.parseLong(String.valueOf(properties.getProperty("default.delay.in.seconds")).trim());
        //schedules the task RemindTask, 0=initial delay,  (DELAY_SECONDS*1000)=subsequent rate
        timer.schedule(new RemindTask(), 0, (DELAY_SECONDS * 1000));
    }

    /** TimerTask is the task to perform */
    class RemindTask extends TimerTask {

        // Task's repetition number, gets created from properties - can be also, changed.
        int repetitionNumber = Integer.parseInt(properties.getProperty("default.tries.number"));

        @Override
        public void run() {
            /** repetitionNumber listener  */
            if (repetitionNumber > 0) {
                /** method that does the job/calls - prints the time in hh:mm:ss as well
                 * to log in the console each request time attempt.  */
                doApplyWeatherInfo(repetitionNumber);
                // counter down of repetitionNumber to intercept the repetition number.  */
                repetitionNumber--;
            } else {
                //  A message to indicate the end of the tasks
                System.out.println("\nTime's up!");
                // timer.cancel();
                // Stops the thread (and everything else)
                System.exit(0);
            }
        }
    }

    /**
     * @return: Properties
     */
    private Properties loadAppProperties(){
        Properties p = null;
        try {
            //  loads properties from property file - given as parameter.
            p = PropertiesUtil.loadProperties("app.properties");
        } catch (IOException e) {
            // Logs the error
            e.printStackTrace();
        }
        return p;
    }

    /**
     * Private method to create the path.
     * It uses the builder design pattern to do so.
     * @param properties
     * @return: PathBuilder
     */
    private PathBuilder createPath(Properties properties){
        // returns the builder of the path to weather api which is built  step bey step processing.
        return new PathBuilder.Build(properties.getProperty("appid"), properties.getProperty("host"))
                .withDataRelativePath(properties.getProperty("data.relative.path"))
                .withProtocol(properties.getProperty("protocol"))
                .withLat(properties.getProperty("lat"))
                .withLon(properties.getProperty("lon"))
                .build();
    }


    /**
     *
     * @param repetitionNumber: parameter
     */
    public void doApplyWeatherInfo(int repetitionNumber){
            // prints the thread and counter down in the console
            System.out.print("\nINTO THREAD - countdown: (" + repetitionNumber + "), ");
            // prints the time in yyy:mm:dd HH:MM:SS
            DateUtil.printCurrentTime();
            // passes the builder in the asynchronous http client to get the weather
            String response = AsynchronousHttpClient.clientResponse(builder);
            // converts the response to json
            JSONObject weatherJsonResponse = JsonUtil.convertFromText(response);
            // loads some information to a dto java object
            WeatherDTO wDto = new WeatherDTO(weatherJsonResponse);
            // loads the phone number from properties, puts the retrieved temperature from the api's weather response
            String message = SmsUtil.buildMessage(wDto.getTemp(), properties.getProperty("phone.number"));
            // prints the message which is to be sent in the console.
            System.out.println("message to send: "+ message);
            // loads from properties the application id and secret,formats it in the proper form
            String beforeEncryption = (properties.getProperty("application.id")).concat(":").concat(properties.getProperty("application.secret"));
            // encrypts with Base64 the application id, secret
            String afterEncryption = Base64EncodeDecodeUtil.encode(beforeEncryption);
            try{
                // requests for the authentication token - a http client call to api
                Optional<JSONObject> jsonToken = SmsUtil.authToken(afterEncryption);
                // if is retrieved and not null value
                if(jsonToken.isPresent()){
                    // get the json object
                     JSONObject jsonResponseObject = jsonToken.get();
                     // asks for the token existence
                     if(jsonResponseObject.containsKey("access_token")){
                         // good to go, so gets the token
                         String accessToken = (String) jsonResponseObject.get("access_token");
                         // sends the sms passing the authentication token and the body message
                         Optional<JSONObject> optionalSmsResponse = SmsUtil.sendSms(accessToken, message);
                         // if the response is ok
                         if(optionalSmsResponse.isPresent()){
                             // extracts it
                             JSONObject jsonSmsResponse = optionalSmsResponse.get();
                             // looks for its/response status
                             if(jsonSmsResponse.containsKey("status")){
                                 // prints the successful status for debugging reasons
                                 System.out.println("SUCCESSFULLY Response status:\t" + jsonSmsResponse.get("status"));
                             }
                         }
                    } else {
                         // error in authentication process. Prints what is the error
                         if (((long)jsonResponseObject.get("status")) != 200){
                             System.out.println("SMS ERROR: Status:\t" +((long)jsonResponseObject.get("status")));
                             System.out.println("SMS ERROR Response:\t" +jsonResponseObject.toJSONString());
                         }
                    }
                }
                // wrapped with a IOExcetion for the method sendSms
            } catch (IOException e) {
                // prints the error
                e.printStackTrace();
            }

    }




}
