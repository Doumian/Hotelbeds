package com.example.hotelbeds.timecalculation.service.impl;

import com.example.hotelbeds.timecalculation.RFC1123Timestamps;
import com.example.hotelbeds.timecalculation.exceptions.RFC1123ParsingException;
import com.example.hotelbeds.timecalculation.service.TimeCalculatorService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class TimeCalculatorServiceImpl implements TimeCalculatorService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

    @Override
    public long getMinutesBetweenTimestamps(RFC1123Timestamps rfc1123Timestamps) throws RFC1123ParsingException {

        ZonedDateTime zoneDate1;
        ZonedDateTime zoneDate2;

        try {
            zoneDate1 = ZonedDateTime.parse(rfc1123Timestamps.getTimeStamp1(), DATE_TIME_FORMATTER);
            zoneDate2 = ZonedDateTime.parse(rfc1123Timestamps.getTimeStamp2(), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new RFC1123ParsingException(rfc1123Timestamps.getTimeStamp1(), rfc1123Timestamps.getTimeStamp2());
        }

        long durationInMinutes = Duration.between(zoneDate1, zoneDate2).toMinutes();
        return Math.abs(Math.floorDiv(durationInMinutes, 1));
    }
}
