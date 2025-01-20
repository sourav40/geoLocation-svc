package com.geolocation.controller.geolocation;

import com.geolocation.entity.GeoLocation;
import com.geolocation.service.GeoLocationService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GeoLocationControllerTest {

    @InjectMocks
    private GeoLocationController geoLocationController;

    @Mock
    private GeoLocationService geoLocationService;

    private final String ipAddress = "192.168.1.1";
    private final GeoLocation geoLocation = new GeoLocation();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGeoLocation_found() {
        when(geoLocationService.findByIpAddress(ipAddress)).thenReturn(Optional.of(geoLocation));

        Response response = geoLocationController.getGeoLocation(ipAddress);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(geoLocation, response.getEntity());
    }

    @Test
    void testGetGeoLocation_notFound() {
        when(geoLocationService.findByIpAddress(ipAddress)).thenReturn(Optional.empty());

        Response response = geoLocationController.getGeoLocation(ipAddress);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
