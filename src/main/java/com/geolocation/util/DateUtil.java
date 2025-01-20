package com.geolocation.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public final class DateUtil {

    private DateUtil() {

    }

    /**
     * DateUtility to check if the created date is past five minute.
     *
     * @param createdDate
     * @return
     */
    public static boolean checkIfCreatedDateIsPastFiveMinute(Date createdDate) {
        Instant now = Instant.now();
        return createdDate.toInstant().isBefore(now.minus(5, ChronoUnit.MINUTES));
    }
}
