package com.example.hotelbeds.monitoring.impl;

import com.example.hotelbeds.common.constants.LogConstants;
import com.example.hotelbeds.log.service.LogService;
import com.example.hotelbeds.monitoring.MonitoringService;
import com.example.hotelbeds.monitoring.exceptions.WatchServiceRegisterDirectoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Slf4j
@Service
public class MonitoringServiceImpl implements MonitoringService {

    private final LogService logService;
    private final WatchService watchService;

    public MonitoringServiceImpl(LogService logService) throws IOException {
        this.logService = logService;
        watchService = FileSystems.getDefault().newWatchService();
    }

    @Override
    public void startMonitoring() throws InterruptedException, WatchServiceRegisterDirectoryException {

        log.info(" --- MONITORING LOG FOLDER STARTED --- ");

        Path dir = getDirectoryPath();
        registerDirectory(dir);

        WatchKey key;
        while ((key = watchService.take()) != null) {

            iterateOverEvents(key);
            key.reset();
        }
    }

    private static Path getDirectoryPath() {
        return Paths.get(LogConstants.LOG_MONITORING_DIRECTORY);
    }

    private void registerDirectory(Path dir) throws WatchServiceRegisterDirectoryException {
        try {
            dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            throw new WatchServiceRegisterDirectoryException(e.getMessage());
        }
    }

    private void iterateOverEvents(WatchKey key)  {
        for (WatchEvent<?> event : key.pollEvents()) {
            WatchEvent.Kind<?> kind = event.kind();
            readLogFileOnCreateOrModifyEvent(event, kind);
        }
    }

    private void readLogFileOnCreateOrModifyEvent(WatchEvent<?> event, WatchEvent.Kind<?> kind)  {
        if (kind == StandardWatchEventKinds.ENTRY_CREATE || kind == StandardWatchEventKinds.ENTRY_MODIFY) {
            Path file = (Path) event.context();
            logService.readLogFile(file.getFileName().toString());
        }
    }

}
