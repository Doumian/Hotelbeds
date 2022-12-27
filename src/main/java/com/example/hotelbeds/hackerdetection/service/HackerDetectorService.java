package com.example.hotelbeds.hackerdetection.service;

public interface HackerDetectorService {
    String parseLine(String line);

    void clearHackerAttempts();
}