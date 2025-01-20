package com.geolocation.util;

import com.geolocation.entity.GeoLocation;

public final class GeoLocationUtility {

    private GeoLocationUtility() {

    }

    /**
     * Utility function to update existing getLocation object with newly fetched values.
     *
     * @param geoLocation
     * @param fetchedGeoLocation
     * @return
     */
    public static void getUpdatedGeoLocation(GeoLocation geoLocation, GeoLocation fetchedGeoLocation) {

        geoLocation.setAs(fetchedGeoLocation.getAs());
        geoLocation.setCity(fetchedGeoLocation.getCity());
        geoLocation.setCountry(fetchedGeoLocation.getCountry());
        geoLocation.setCountryCode(fetchedGeoLocation.getCountryCode());
        geoLocation.setIsp(fetchedGeoLocation.getIsp());
        geoLocation.setLat(fetchedGeoLocation.getLat());
        geoLocation.setLon(fetchedGeoLocation.getLon());
        geoLocation.setOrg(fetchedGeoLocation.getOrg());
        geoLocation.setRegion(fetchedGeoLocation.getRegion());
        geoLocation.setRegionName(fetchedGeoLocation.getRegionName());
        geoLocation.setTimezone(fetchedGeoLocation.getTimezone());
        geoLocation.setZip(fetchedGeoLocation.getZip());

    }
}
