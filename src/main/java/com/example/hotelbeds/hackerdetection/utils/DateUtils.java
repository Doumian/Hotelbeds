package com.example.hotelbeds.hackerdetection.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateUtils {
    private DateUtils() {
    }

    public static Long getMinutesBetweenEpochs(long epochDate1, long epochDate2) {
        Instant instant1;
        Instant instant2;

        try {
            instant1 = Instant.ofEpochSecond(epochDate1);
            instant2 = Instant.ofEpochSecond(epochDate2);
        } catch (DateTimeParseException ex) {
            log.error("Error parsing long: " + ex.getMessage());
            return null;
        }

        long durationInMinutes = Duration.between(instant1, instant2).toMinutes();

        return Math.abs(Math.floorDiv(durationInMinutes, 1));
    }

}
