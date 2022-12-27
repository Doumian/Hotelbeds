package com.example.hotelbeds.log.service;
public interface LogService {
    String generateRandomLogfile(int hackerAttemptProbability);

    void readLogFile(String logFilename) ;
}
