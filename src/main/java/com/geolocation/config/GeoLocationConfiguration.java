package com.geolocation.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class GeoLocationConfiguration extends Configuration {

    /**
     * Database config.
     */
    @NotNull
    private DataSourceFactory database;

    public DataSourceFactory getDatabase() {
        return database;
    }

    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }

    /**
     * External API to get GeoLocation based on IP Address.
     */
    @NotNull
    private String geoLocationUrl;

    @JsonProperty
    public String getGeoLocationUrl() {
        return geoLocationUrl;
    }

    @JsonProperty
    public void setGeoLocationUrl(String geoLocationUrl) {
        this.geoLocationUrl = geoLocationUrl;
    }

}
