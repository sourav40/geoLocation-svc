package com.geolocation.controller.health;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthTest {

    private final Health health = new Health();

    @Test
    void testGetStatus() {
        Response response = health.getStatus();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("API geolocation-svc is up and running ...", response.getEntity());
    }
}
