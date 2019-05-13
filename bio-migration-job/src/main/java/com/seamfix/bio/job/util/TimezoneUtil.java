package com.seamfix.bio.job.util;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class TimezoneUtil {

    private TimezoneUtil() {}

    public static ZonedDateTime getZonedTime(Long timeStamp, String zoneId) {
            Instant i = Instant.ofEpochMilli(timeStamp);

                if (zoneId == null || zoneId.trim().isEmpty()) {
                    return ZonedDateTime.ofInstant(i, ZoneId.systemDefault());
                } else {
                    try {
                        return ZonedDateTime.ofInstant(i, ZoneId.of(zoneId));
                    } catch (DateTimeException ex) {
                        return ZonedDateTime.ofInstant(i, ZoneId.systemDefault());
                    }
                }
    }
}
