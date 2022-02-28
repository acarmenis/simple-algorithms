package org.weather.api.client;

import org.weather.api.utils.PathBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class AsynchronousHttpClient {

    /** The request time out in seconds */
    private static final long REQUEST_TIMEOUT = 10;

    /** The response time out in seconds */
    private static final long RESPONSE_TIMEOUT = 20;

    /**
     * Http Client builder
     * @return: HttpClient
     */
    private static HttpClient httpClient(){
        return  HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(REQUEST_TIMEOUT))
                .build();
    }

    /**
     * @param builder: the builder path
     * @return: final string response
     */
    public static String clientResponse(PathBuilder builder){

        // the final response - json string.
        String finalResponse = null;

        //
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(builder.toString()))
                .build();

        // does the async non-blocking call to the weather api
        CompletableFuture<HttpResponse<String>> response = httpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            // extracts the stringified response
            finalResponse = response.thenApply(HttpResponse::body).get(RESPONSE_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        // return the json string
        return finalResponse;
    }


}
