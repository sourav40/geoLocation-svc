package com.geolocation.controller.geolocation;

import com.geolocation.entity.GeoLocation;
import com.geolocation.service.GeoLocationService;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.geolocation.util.Constant.GEO_LOCATION;
import static com.geolocation.util.Constant.IP_ADDRESS;

@Slf4j
@Path(GEO_LOCATION)
@Produces(MediaType.APPLICATION_JSON)
public class GeoLocationController {

    /**
     * GeoLocationService.
     */
    private final GeoLocationService geoLocationService;

    public GeoLocationController(GeoLocationService geoLocationService) {
        this.geoLocationService = geoLocationService;
    }


    /**
     * API to fetch GeoLocation from provided IP Address.
     *
     * @param ipAddress
     * @return
     */
    @GET
    @Path(IP_ADDRESS)
    @UnitOfWork
    public Response getGeoLocation(@Valid @NotNull @PathParam("ipAddress") String ipAddress) {

        log.info("fetching geoLocation for IP: {}", ipAddress);

        Optional<GeoLocation> geoLocation = geoLocationService.findByIpAddress(ipAddress);

        if (geoLocation.isPresent()) {
            return Response.ok(geoLocation.get()).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
