package com.example.bookmyshow_be.Utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {

    private DateTimeUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a date string to the start of the day in a given timezone and shifts it to UTC.
     *
     * @param dateString the date string in "yyyy-MM-dd" format
     * @param zoneId     the target timezone (e.g., "Asia/Kolkata")
     * @return the UTC start of the day as an ISO-8601 string (e.g., "2024-11-19T18:30:00.000Z")
     */
    public static String toUtcStartOfDay(String dateString, String zoneId) {
        // Parse the date string into LocalDate
        LocalDate localDate = LocalDate.parse(dateString);

        // Calculate the start of the day in the target timezone
        ZonedDateTime startOfDayInZone = localDate.atStartOfDay(ZoneId.of(zoneId));

        // Convert to UTC
        Instant utcInstant = startOfDayInZone.toInstant();

        // Format to ISO-8601 string
        return DateTimeFormatter.ISO_INSTANT.format(utcInstant);
    }

    /**
     * Converts a UTC date-time string to the start of the day in a specific timezone.
     *
     * @param utcDateTimeString the UTC date-time string in ISO-8601 format (e.g., "2024-11-19T18:30:00.000Z")
     * @param zoneId            the target timezone (e.g., "Asia/Kolkata")
     * @return the start of the day in the target timezone as an ISO-8601 string
     */
    public static String toStartOfDayInZone(String utcDateTimeString, String zoneId) {
        // Parse the UTC date-time string into Instant
        Instant utcInstant = Instant.parse(utcDateTimeString);

        // Convert to the target timezone
        ZonedDateTime zonedDateTime = utcInstant.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.of(zoneId));

        // Get the start of the day in the target timezone
        ZonedDateTime startOfDay = zonedDateTime.toLocalDate().atStartOfDay(ZoneId.of(zoneId));

        // Format to ISO-8601 string
        return DateTimeFormatter.ISO_INSTANT.format(startOfDay.toInstant());
    }

    /**
     * Converts a date string to a `Date` object set to the start of the day in UTC.
     *
     * @param dateString the date string in "yyyy-MM-dd" format
     * @param zoneId     the target timezone (e.g., "Asia/Kolkata")
     * @return the `Date` object representing the start of the day in UTC
     */
    public static Date toUtcDate(String dateString, String zoneId) {
        // Parse and shift to UTC as Instant
        Instant utcInstant = LocalDate.parse(dateString)
                .atStartOfDay(ZoneId.of(zoneId))
                .toInstant();

        // Convert Instant to Date
        return Date.from(utcInstant);
    }

    /**
     * Converts the current date-time to the start of the day in a given timezone and shifts it to UTC for storage.
     *
     * @param zoneId the target timezone (e.g., "Asia/Kolkata")
     * @return the `Date` object representing the start of the day in UTC
     */
    public static Date getStartOfDayInUtcFromZone(String zoneId) {
        // Get current date-time in UTC
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneOffset.UTC);

        // Convert UTC to the target timezone (e.g., Asia/Kolkata)
        ZonedDateTime zoneNow = utcNow.withZoneSameInstant(ZoneId.of(zoneId));

        // Get the start of the day in the target timezone
        ZonedDateTime startOfDayInZone = zoneNow.toLocalDate().atStartOfDay(ZoneId.of(zoneId));

        // Convert the start of the day back to UTC for storage
        ZonedDateTime startOfDayInUtc = startOfDayInZone.withZoneSameInstant(ZoneOffset.UTC);

        // Convert ZonedDateTime to java.util.Date for compatibility
        return Date.from(startOfDayInUtc.toInstant());
    }
}

