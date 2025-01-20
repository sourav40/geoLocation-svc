package com.geolocation.controller.health;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import static com.geolocation.util.Constant.HEALTH;

@Slf4j
@Path(HEALTH)
@Produces(MediaType.APPLICATION_JSON)
public class Health {

    /**
     * API to check if the service is running.
     *
     * @return
     */
    @GET
    public Response getStatus() {
        return Response.ok("API geolocation-svc is up and running ...").build();
    }

}
