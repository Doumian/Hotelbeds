package com.example.hotelbeds.monitoring.impl;

import com.example.hotelbeds.monitoring.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class CustomRunner implements CommandLineRunner {

    private final MonitoringService monitoringService;

    @Autowired
    public CustomRunner(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Override
    public void run(String... args) throws Exception {
        monitoringService.startMonitoring();
    }

}
