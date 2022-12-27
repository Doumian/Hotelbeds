package com.example.hotelbeds.hackerdetection.service.impl;

import com.example.hotelbeds.common.constants.LogConstants;
import com.example.hotelbeds.common.enums.SignInResult;
import com.example.hotelbeds.hackerdetection.model.FailedLoginAttempts;
import com.example.hotelbeds.hackerdetection.service.HackerDetectorService;
import com.example.hotelbeds.hackerdetection.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Slf4j
public class HackerDetectorServiceImpl implements HackerDetectorService {
    public static final int MAX_MINUTES = 5;
    public static final int MAX_ATTEMPTS = 5;
    private final Map<String, FailedLoginAttempts> failedLoginAttemptsMap = new HashMap<>();
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    @Override
    public String parseLine(String line) {

        String[] log = line.split(LogConstants.SEPARATOR);

        if (isLogLengthValid(log)) {

            SignInResult signInResult = parseSignInResult(log[2]);

            if (hasSignInFailed(signInResult)) {

                String ipv4 = log[0];

                if (isIPv4Valid(ipv4)) {

                    long epoch = parseEpochStringToLong(log[1]);

                    if (isEpochValid(epoch)) {
                        return recordFailedLoginAttempt(ipv4, epoch);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void clearHackerAttempts() {
        this.failedLoginAttemptsMap.clear();
    }

    private static boolean isLogLengthValid(String[] log) {
        return LogConstants.EXPECTED_LOG_LENGTH <= log.length;
    }

    private static boolean hasSignInFailed(SignInResult signInResult) {
        return SignInResult.SIGNIN_FAILURE == signInResult;
    }

    private boolean isIPv4Valid(final String ip) {
        return IPV4_PATTERN.matcher(ip).matches();
    }

    private static boolean isEpochValid(long epoch) {
        return 0 != epoch;
    }

    private SignInResult parseSignInResult(String input) {
        try {
            return SignInResult.valueOf(input);
        } catch (IllegalArgumentException e) {
            log.error("Error parsing SignIn Enum: " + e.getMessage());
            return null;
        }
    }

    private long parseEpochStringToLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            log.error("Error parsing long: " + e.getMessage());
            return 0;
        }
    }

    private String recordFailedLoginAttempt(String ip, long epoch) {

        FailedLoginAttempts attempts = failedLoginAttemptsMap.computeIfAbsent(ip, failedLoginAttempt -> new FailedLoginAttempts(epoch));

        attempts.incrementCount();

        if (hasExceededMaximumLoginAttempts(attempts)) {

            Long minutesBetweenEpochs = DateUtils.getMinutesBetweenEpochs(epoch, attempts.getFirstAttemptTime());

            if (hasAttemptedLoginWithin5Minutes(minutesBetweenEpochs)) {
                return ip;
            }
        }
        return null;
    }

    private static boolean hasAttemptedLoginWithin5Minutes(Long minutesBetweenEpochs) {
        return minutesBetweenEpochs != null && minutesBetweenEpochs < MAX_MINUTES;
    }

    private static boolean hasExceededMaximumLoginAttempts(FailedLoginAttempts attempts) {
        return attempts.getCount() >= MAX_ATTEMPTS;
    }

}
