package com.geolocation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geolocation.entity.GeoLocation;
import jakarta.ws.rs.client.Client;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JerseyClient {

    private JerseyClient() {

    }

    /**
     * This method will call the External API to get GeoLocation based on IP Address provided.
     *
     * @param geoLocationUrl
     * @param ipAddress
     * @param client
     * @return
     */
    public static GeoLocation fetchFromExternalAPI(final String geoLocationUrl, final String ipAddress, Client client) {
        try {
            String response = client.target(geoLocationUrl + ipAddress)
                    .request()
                    .get(String.class);

            return new ObjectMapper().readValue(response, GeoLocation.class);

        } catch (Exception e) {
            log.error("Exception occurred while fetching the GeoLocation from External API. Reason: {}", e.toString());
            return null;
        }
    }

}
