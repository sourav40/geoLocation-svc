package com.geolocation.dao;

import com.geolocation.entity.GeoLocation;
import com.geolocation.util.DateUtil;
import com.geolocation.util.JerseyClient;
import io.dropwizard.hibernate.AbstractDAO;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.client.Client;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.Optional;
import java.util.random.RandomGenerator;

import static com.geolocation.util.Constant.SUCCESS;

@Slf4j
public class GeoLocationDAO extends AbstractDAO<GeoLocation> {

    /**
     * SessionFactory.
     */
    private final SessionFactory sessionFactory;

    /**
     * JerseyClient to fetch from External API.
     */
    private final Client client;

    /**
     * GeoLocationUrl.
     */
    private final String geoLocationUrl;

    public GeoLocationDAO(SessionFactory sessionFactory, Client client, String geoLocationUrl) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
        this.client = client;
        this.geoLocationUrl = geoLocationUrl;
    }

    /**
     * This method will fetch the GeoLocation from Database and if not found in Database will call the External API and then store
     * the data to GeoLocation Database.
     *
     * @param ipAddress
     * @return
     */
    public Optional<GeoLocation> findByIpAddressFromDatabase(final String ipAddress) {
        try {

            GeoLocation geoLocation = (GeoLocation) namedQuery("findByIpAddress").setParameter("ipAddress", ipAddress)
                    .getSingleResult();

            /**
             * Check if geoLocation created date is past 5 minute and if true update existing object with new object
             * by calling External API.
             */
            if (DateUtil.checkIfCreatedDateIsPastFiveMinute(geoLocation.getCreatedDate())) {
                Optional<GeoLocation> updatedGeoLocation = fetchFromExternalAPI(geoLocationUrl, ipAddress, client, geoLocation.getId());
                return updatedGeoLocation;
            }

            return Optional.ofNullable(geoLocation);

        } catch (NoResultException e) {

            Optional<GeoLocation> geoLocation = fetchFromExternalAPI(geoLocationUrl, ipAddress, client, null);
            return geoLocation;

        } catch (Exception e) {
            log.error("Exception occurred fetching geoLocation to Database. Reason: {}", e.toString());
            return Optional.empty();
        }
    }

    private Optional<GeoLocation> fetchFromExternalAPI(final String geoLocationUrl, final String ipAddress, Client client, Long id) {

        GeoLocation geoLocation = JerseyClient.fetchFromExternalAPI(geoLocationUrl, ipAddress, client);

        if (geoLocation != null && SUCCESS.equals(geoLocation.getStatus())) {
            geoLocation.setId((id == null) ? RandomGenerator.getDefault().nextLong() : id);
            geoLocation.setIpAddress(ipAddress);
            geoLocation.setCreatedDate(new Date());
            save(geoLocation);

            return Optional.ofNullable(geoLocation);
        }
        return null;
    }


    /**
     * This method will save the GeoLocation fetched from External API to Database.
     *
     * @param geoLocation
     */
    private void save(final GeoLocation geoLocation) {
        try {
            persist(geoLocation);
        } catch (Exception e) {
            log.error("Exception occurred saving geoLocation to Database. Reason: {}", e.toString());
        }
    }
}
