package com.geolocation.service;

import com.geolocation.dao.GeoLocationDAO;
import com.geolocation.entity.GeoLocation;
import com.geolocation.util.DateUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GeoLocationService {

    private final GeoLocationDAO geoLocationDAO;
    private final LoadingCache<String, Optional<GeoLocation>> cache;

    /**
     * Maximum entries in cache that can be stored.
     */
    private static final int CACHE_MAX_SIZE = 1000;

    /**
     * Expiration time in minutes for cache.
     */
    private static final int CACHE_EXPIRATION_MINUTES = 1;

    /**
     * @param geoLocationDAO
     */
    public GeoLocationService(GeoLocationDAO geoLocationDAO) {

        this.geoLocationDAO = geoLocationDAO;

        /**
         * cache to store fetched GeoLocation for time interval defined.
         */
        cache = CacheBuilder.newBuilder()
                .maximumSize(CACHE_MAX_SIZE)
                .expireAfterAccess(CACHE_EXPIRATION_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public Optional<GeoLocation> load(String ipAddress) {
                        return findByIpAddressFromDatabase(ipAddress);
                    }

                });
    }

    /**
     * This method will return the GeoLocation based on IP Address first from Cache created and only will fetch from
     * Database in case of cache miss.
     *
     * @param ipAddress
     * @return
     */
    public Optional<GeoLocation> findByIpAddress(final String ipAddress) {
        try {
            Optional<GeoLocation> cachedGeoLocation = cache.get(ipAddress);
            if (DateUtil.checkIfCreatedDateIsPastFiveMinute(cachedGeoLocation.get().getCreatedDate())) {
                return findByIpAddressFromDatabase(ipAddress);
            }
            return cachedGeoLocation;
        } catch (Exception e) {
            log.error("Exception while fetching the GeoLocation with IP: {}, and reason: {}", ipAddress, e.toString());
            return Optional.empty();
        }
    }

    /**
     * This method will call DAO to fetch the GeoLocation from Database.
     *
     * @param ipAddress
     * @return
     */
    private Optional<GeoLocation> findByIpAddressFromDatabase(final String ipAddress) {

        log.info("Cache miss. Fetching the GeoLocation from Database for IP: {}", ipAddress);

        return geoLocationDAO.findByIpAddressFromDatabase(ipAddress);
    }

}
