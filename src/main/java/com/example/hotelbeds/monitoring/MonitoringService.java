package com.example.hotelbeds.monitoring;

import com.example.hotelbeds.monitoring.exceptions.WatchServiceRegisterDirectoryException;

import java.io.IOException;

public interface MonitoringService {

    void startMonitoring() throws IOException, InterruptedException, WatchServiceRegisterDirectoryException;

}
