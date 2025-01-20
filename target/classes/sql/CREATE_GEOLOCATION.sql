CREATE TABLE IF NOT EXISTS GeoLocation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255),
    country VARCHAR(255) NOT NULL,
    country_code VARCHAR(10) NOT NULL,
    region VARCHAR(255) NOT NULL,
    region_name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    timezone VARCHAR(255) NOT NULL,
    isp VARCHAR(255) NOT NULL,
    organization VARCHAR(255) NOT NULL,
    autonomous_system VARCHAR(255) NOT NULL,
    ip_address VARCHAR(255) NOT NULL UNIQUE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);