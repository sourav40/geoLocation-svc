package com.geolocation.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "GeoLocation")
@NamedQueries({@NamedQuery(
        name = "findByIpAddress",
        query = "SELECT g FROM GeoLocation g WHERE g.ipAddress = :ipAddress")})
public class GeoLocation {


    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "country")
    private String country;

    @NotNull
    @Column(name = "country_code")
    private String countryCode;

    @NotNull
    @Column(name = "region")
    private String region;

    @NotNull
    @Column(name = "region_name")
    private String regionName;

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "zip")
    private String zip;

    @NotNull
    @Column(name = "latitude")
    private double lat;

    @NotNull
    @Column(name = "longitude")
    private double lon;

    @NotNull
    @Column(name = "timezone")
    private String timezone;

    @NotNull
    @Column(name = "isp")
    private String isp;

    @NotNull
    @Column(name = "organization")
    private String org;

    @NotNull
    @Column(name = "autonomous_system")
    private String as;

    @NotNull
    @Column(name = "ip_address", unique = true)
    private String ipAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Transient
    private String query;
}
