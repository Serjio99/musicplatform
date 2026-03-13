package com.musicplatform.util;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private DateUtils() {
    }

    public static OffsetDateTime nowUtc() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    public static String formatIso(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return ISO_FORMATTER.format(dateTime);
    }
}