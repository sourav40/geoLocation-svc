package com.geolocation.service;

import com.geolocation.dao.GeoLocationDAO;
import com.geolocation.entity.GeoLocation;
import com.google.common.cache.LoadingCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GeoLocationServiceTest {

    @Mock
    private GeoLocationDAO geoLocationDAO;

    @InjectMocks
    private GeoLocationService geoLocationService;

    private final String ipAddress = "192.168.1.1";
    private final GeoLocation geoLocation = new GeoLocation();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIpAddress_cacheHit() throws Exception {
        Optional<GeoLocation> geoLocationOptional = Optional.of(geoLocation);

        LoadingCache<String, Optional<GeoLocation>> cacheMock = mock(LoadingCache.class);
        when(cacheMock.get(ipAddress)).thenReturn(geoLocationOptional);

        geoLocationService = new GeoLocationService(geoLocationDAO);
        Field cacheField = GeoLocationService.class.getDeclaredField("cache");
        cacheField.setAccessible(true);
        cacheField.set(geoLocationService, cacheMock);

        Optional<GeoLocation> result = geoLocationService.findByIpAddress(ipAddress);

        assertEquals(geoLocationOptional, result);
        verify(cacheMock).get(ipAddress);
        verifyNoInteractions(geoLocationDAO);
    }

}
