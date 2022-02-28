package org.weather.api.utils;

import okhttp3.*;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class SmsUtil {

    private static final Double THRESHOLD_TEMPERATURE = 20.0;

    public static Optional<JSONObject> authToken(String encodedString) throws IOException {

        Optional<JSONObject> optionalJson = Optional.empty();

        Optional<String> checkEncodedStringNull = Optional.ofNullable(encodedString);

        if(checkEncodedStringNull.isPresent()) {

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

            RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");

            Request request = new Request.Builder()
                    .url("https://auth.routee.net/oauth/token")
                    .post(body)
                    .addHeader("authorization", ("Basic".concat(" ").concat(checkEncodedStringNull.get())))
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            try (Response response = client.newCall(request).execute()) {

                 String responseBody = Objects.requireNonNull(response.body()).string();

                 return Optional.of(JsonUtil.convertFromText(responseBody));
            }

        }

        return optionalJson;
    }


    public static Optional<JSONObject> sendSms(String authorization, String message) throws IOException {

        Optional<JSONObject> optionalJson = Optional.empty();

        Optional<String> checkAuthorizationNull = Optional.ofNullable(authorization);

        Optional<String> checkMessageNull = Optional.ofNullable(message);

        if(checkAuthorizationNull.isPresent() && checkMessageNull.isPresent()) {

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");


            RequestBody body = RequestBody.create(mediaType, checkMessageNull.get());

            Request request = new Request.Builder()
                    .url("https://connect.routee.net/sms")
                    .post(body)
                    .addHeader("authorization", "Bearer".concat(" ").concat(checkAuthorizationNull.get()))
                    .addHeader("content-type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {

                String responseBody = Objects.requireNonNull(response.body()).string();

                return Optional.of(JsonUtil.convertFromText(responseBody));
            }
        }

        return optionalJson;
    }

    public static String buildMessage(Double currentTemperature, String phoneNumber) {

        String message = (Double.compare(currentTemperature, THRESHOLD_TEMPERATURE) < 0)
                            // currentTemperature < THRESHOLD_TEMPERATURE
                            ? "Andreas Karmenis, Temperature less than 20C. Live temperature: "+ currentTemperature +"C."
                            // currentTemperature >= THRESHOLD_TEMPERATURE
                            : "Andreas Karmenis, Temperature equal or more than 20C. Live temperature: ["+ currentTemperature +"C.";

        JSONObject o = new JSONObject();
        o.put("body", message);
        o.put("to", phoneNumber);
        o.put("from", "amdTelecom");

       return o.toJSONString();

    }
}
