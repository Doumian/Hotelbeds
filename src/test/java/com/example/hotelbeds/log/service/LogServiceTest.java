package com.example.hotelbeds.log.service;

import com.example.hotelbeds.hackerdetection.service.HackerDetectorService;
import com.example.hotelbeds.log.service.impl.LogServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.example.hotelbeds.common.constants.LogConstants.LOG_DIRECTORY;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    private LogService logService;

    @Mock
    private HackerDetectorService hackerDetectorService;

    @BeforeEach
    public void setup() {
        logService = new LogServiceImpl(hackerDetectorService);
    }

    @AfterEach
    public void clearDirectory() throws IOException {

        Files.walk(Paths.get(LOG_DIRECTORY))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void shouldGenerateRandomLogfile() throws IOException {

        Path directory = Paths.get(LOG_DIRECTORY);

        int initialNumberOfFiles = getNumberOfFiles(directory);

        logService.generateRandomLogfile(1);

        int actualNumberOfFiles = getNumberOfFiles(directory);

        assertEquals(initialNumberOfFiles + 1, actualNumberOfFiles);

    }

    private static int getNumberOfFiles(Path directory) throws IOException {
        List<Path> files = Files.list(directory).toList();

        return files.size();
    }
}