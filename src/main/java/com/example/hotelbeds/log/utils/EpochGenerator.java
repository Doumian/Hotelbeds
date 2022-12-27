package com.example.hotelbeds.log.utils;

import java.util.Calendar;

public class EpochGenerator {

    private EpochGenerator() {
    }

    public static long generateEpoch(int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -min);
        return calendar.getTimeInMillis() / 1000L;
    }

}
