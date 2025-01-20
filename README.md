# GeoLocation Service

## Overview

The GeoLocation Service is a Dropwizard service that provides geo-location information based on IP addresses. It fetches data from a database, and in case of a cache or database miss, it retrieves information from an external API and stores it in the database for future use.

## Features

- Fetch geo-location details by IP address.
- Cache implementation to reduce database and external API calls.
- External API fallback mechanism.
- Health check endpoint to verify service status.


## Endpoints

### 1. GeoLocation API

**Path:** `/geoLocation/{ipAddress}`  
**Method:** `GET`  
**Description:** Fetches geo-location information for the provided IP address.  
**Response:**
- **200 OK**: Returns the geo-location details.
- **404 NOT FOUND**: If no information is found for the given IP address.

---

### 2. Health Check API

**Path:** `/health`  
**Method:** `GET`  
**Description:** Returns the health status of the application.  
**Response:**
- **200 OK**: `"API geolocation-svc is up and running ..."`

---

## Technologies Used

- **Java 17**: Programming language.
- **Dropwizard**: Framework for creating RESTful web services.
- **Hibernate**: ORM for database operations.
- **Guava Cache**: Caching solution for geo-location lookups.
- **Mockito + JUnit 5**: Unit testing framework.


## Build and Run Instructions

### 1. Clone the repository

`git clone <repository_url>` <br>
`cd geolocation-service`

## MySQL 
Need to create the database geoLocation in mysql and config (username, password, port, database name) can be changed from config.yml file under resources folder.

## Command to build and run project
Build: `mvn clean package` <br>
Run: `java -jar target/geolocation-svc-1.0-SNAPSHOT.jar server src/main/resources/config.yml` <br><br>
Note: You need to change terminal to your project folder before running this command.


