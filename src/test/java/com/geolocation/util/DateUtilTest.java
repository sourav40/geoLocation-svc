package com.geolocation.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void testCheckIfCreatedDateIsPastFiveMinute_WhenDateIsMoreThanFiveMinutesOld_ShouldReturnTrue() {
        Instant moreThanFiveMinutesAgo = Instant.now().minus(6, java.time.temporal.ChronoUnit.MINUTES);
        Date createdDate = Date.from(moreThanFiveMinutesAgo);

        assertTrue(DateUtil.checkIfCreatedDateIsPastFiveMinute(createdDate));
    }

    @Test
    void testCheckIfCreatedDateIsPastFiveMinute_WhenDateIsLessThanFiveMinutesOld_ShouldReturnFalse() {
        Instant lessThanFiveMinutesAgo = Instant.now().minus(3, java.time.temporal.ChronoUnit.MINUTES);
        Date createdDate = Date.from(lessThanFiveMinutesAgo);

        assertFalse(DateUtil.checkIfCreatedDateIsPastFiveMinute(createdDate));
    }

    @Test
    void testCheckIfCreatedDateIsPastFiveMinute_WhenDateIsExactlyFiveMinutesOld_ShouldReturnTrue() {
        Instant exactlyFiveMinutesAgo = Instant.now().minus(5, java.time.temporal.ChronoUnit.MINUTES);
        Date createdDate = Date.from(exactlyFiveMinutesAgo);

        assertTrue(DateUtil.checkIfCreatedDateIsPastFiveMinute(createdDate));
    }
}
