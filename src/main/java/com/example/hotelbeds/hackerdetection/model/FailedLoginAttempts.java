package com.example.hotelbeds.hackerdetection.model;

import lombok.Getter;

@Getter
public class FailedLoginAttempts {
    private int count;
    private final long firstAttemptTime;

    public FailedLoginAttempts(long epoch) {
        count = 0;
        firstAttemptTime = epoch;
    }
    public void incrementCount() {
        count++;
    }

}
