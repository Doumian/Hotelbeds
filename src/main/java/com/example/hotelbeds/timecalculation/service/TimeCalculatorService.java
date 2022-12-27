package com.example.hotelbeds.timecalculation.service;

import com.example.hotelbeds.timecalculation.RFC1123Timestamps;
import com.example.hotelbeds.timecalculation.exceptions.RFC1123ParsingException;

public interface TimeCalculatorService {
    long getMinutesBetweenTimestamps(RFC1123Timestamps rfc1123Timestamps) throws RFC1123ParsingException;
}
