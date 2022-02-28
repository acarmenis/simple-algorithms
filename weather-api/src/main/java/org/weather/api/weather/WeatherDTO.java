package org.weather.api.weather;


import lombok.*;
import org.json.simple.JSONObject;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {

    private String name ;
    private String country;
    private Double lon;
    private Double lat;
    private Double temp;

    public WeatherDTO(JSONObject jsonObject) {
        createWeatherDTOFromJSONObject(jsonObject);
    }

    public WeatherDTO fromJson(JSONObject jsonObject){
       createWeatherDTOFromJSONObject(jsonObject);
       return this;
    }

    private void createWeatherDTOFromJSONObject(JSONObject jsonObject){
        this.name = String.valueOf(jsonObject.get("name"));
        JSONObject sys = (JSONObject) jsonObject.get("sys");
        this.country = String.valueOf(sys.get("country"));
        JSONObject coord = (JSONObject) jsonObject.get("coord");
        this.lon = (double) coord.get("lon");
        JSONObject lat = (JSONObject) jsonObject.get("lat");
        this.lat = (double) coord.get("lat");
        JSONObject main = (JSONObject) jsonObject.get("main");
        this.temp = (double) main.get("temp");
    }
}
