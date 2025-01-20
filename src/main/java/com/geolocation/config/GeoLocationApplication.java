package com.geolocation.config;

import com.geolocation.controller.geolocation.GeoLocationController;
import com.geolocation.controller.health.Health;
import com.geolocation.dao.GeoLocationDAO;
import com.geolocation.entity.GeoLocation;
import com.geolocation.service.GeoLocationService;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import jakarta.ws.rs.client.Client;
import org.hibernate.SessionFactory;

public class GeoLocationApplication extends Application<GeoLocationConfiguration> {

    public static void main(String[] args) throws Exception {
        new GeoLocationApplication().run(args);
    }

    /**
     * Code to set the mysql config for storing the GeoLocation object. MySql config are fetched from config.yml file.
     */
    private final HibernateBundle<GeoLocationConfiguration> hibernateBundle =
            new HibernateBundle<>(GeoLocation.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(GeoLocationConfiguration configuration) {
                    return configuration.getDatabase();
                }
            };

    @Override
    public void initialize(Bootstrap<GeoLocationConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(GeoLocationConfiguration geoLocationConfiguration, Environment environment) {

        /**
         * HealthCare API.
         */
        Health health = new Health();
        environment.jersey().register(health);

        /**
         * Session Factory for Database operation.
         */
        SessionFactory sessionFactory = hibernateBundle.getSessionFactory();

        /**
         * Jersey Client to fetch the GeoLocation from External API.
         */
        Client client = new JerseyClientBuilder(environment).build("geoLocation-jersey-client");

        /**
         * Dependency Injection for performing Database operation for GeoLocation.
         */
        GeoLocationDAO geoLocationDAO = new GeoLocationDAO(sessionFactory, client, geoLocationConfiguration.getGeoLocationUrl());
        GeoLocationService geoLocationService = new GeoLocationService(geoLocationDAO);
        GeoLocationController geoLocationController = new GeoLocationController(geoLocationService);

        environment.jersey().register(geoLocationController);

    }
}
