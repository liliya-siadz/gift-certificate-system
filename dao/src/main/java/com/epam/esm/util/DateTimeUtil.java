package com.epam.esm.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

public class DateTimeUtil {
    private static final String ISO_8601_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    private DateTimeUtil() {
    }

    public static LocalDateTime getCurrentDateTimeIso8601() {
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        lastUpdateDate.format(ofPattern(ISO_8601_DATE_TIME_FORMAT_PATTERN));
        return lastUpdateDate;
    }

    public static LocalDateTime convertToDateTimeIso8601(Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        localDateTime.format(ofPattern(ISO_8601_DATE_TIME_FORMAT_PATTERN));
        return localDateTime;
    }

}
