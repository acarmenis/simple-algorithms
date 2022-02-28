package org.weather.api.utils;

/**
 * Immutable class PathBuilder
 */
public class PathBuilder {

    private final String apiKey;
    private final String city;
    private final String country;
    private final String limit;
    private final String protocol;
    private final String host;
    private final String cityInfoPath;
    private final String dataRelativePath;
    private final String lon;
    private final String lat;

    /**
     * private access constructor
     * @param build
     */
    private PathBuilder(Build build) {
        this.apiKey = build.apiKey;
        this.city = build.city;
        this.country = build.country;
        this.limit = build.limit;
        this.protocol = build.protocol;
        this.host = build.host;
        this.cityInfoPath = build.cityInfoPath;
        this.dataRelativePath = build.dataRelativePath;
        this.lon = build.lon;
        this.lat = build.lat;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getLimit() {
        return limit;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public String getCityInfoPath() {
        return cityInfoPath;
    }

    public String getDataRelativePath() {
        return dataRelativePath;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    /**
     * The below method, is responsible for switching between two weather api urls.
     * 1. is for getting the coordinates of the city of Thessaloniki - but currently is not activated.
     *    The reason is that after making a call, the coordinate are saved in the property file.
     *    Since the coordinate of the city cannot be changed, it was decided to avoid that redundancy call for
     *    saving time.
     * 2. The builder, makes use of the second option, that one of querying and getting the temperature
     *    which the method is decided by the filter if on the dataRelativePath field.
     *    If the dataRelativePath field is not null, it creates that url.
     * @return
     */
    @Override
    public String toString() {
        // base url info for both urls, it does though get differentiated according to if logic below.
        StringBuilder sb = new StringBuilder(getProtocol()).append(":").append("//").append(getHost());
        //
        if ((cityInfoPath!=null)&&(!cityInfoPath.isEmpty())){
            sb.append(getCityInfoPath())
              .append("?").append("q").append("=").append(getCity()).append(",").append(getCountry())
              .append("&").append("limit").append("=").append(getLimit());
        }
        // if dataRelativePath is not empty, it appends on the string builder defaults those one which
        // concerns this url
        if ((dataRelativePath!=null)&&(!dataRelativePath.isEmpty())){
            sb.append(getDataRelativePath())
              .append("?").append("lat").append("=").append(getLat())
              .append("&").append("lon").append("=").append(getLon());
        }
        // at the end, it adds what is common one again for either one or the other url
        sb.append("&").append("units").append("=").append("metric").append("&").append("appid").append("=").append(getApiKey());
        //
        return sb.toString();
    }

    /**
     *  inner builder static class
     */
    public static class Build {
        private final String apiKey;
        private String city;
        private String country;
        private String limit;
        private String protocol;
        private final String host;
        private String cityInfoPath;
        private String dataRelativePath;
        private String lon;
        private String lat;

        public Build(String apiKey, String host) {
            this.apiKey = apiKey;
            this.host = host;
        }

        public Build withCity(String city) {
            this.city = city;
            return this;
        }

        public Build withCountry(String country) {
            this.country = country;
            return this;
        }

        public Build withLimit(String limit) {
            this.limit = limit;
            return this;
        }

        public Build withProtocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Build withCityInfoPath(String cityInfoPath) {
            this.cityInfoPath = cityInfoPath;
            return this;
        }

        public Build withDataRelativePath(String dataRelativePath) {
            this.dataRelativePath = dataRelativePath;
            return this;
        }

        public Build withLon(String lon) {
            this.lon = lon;
            return this;
        }

        public Build withLat(String lat) {
            this.lat = lat;
            return this;
        }

        public PathBuilder build() {
            return new PathBuilder(this);
        }

    }

}
