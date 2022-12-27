package com.example.hotelbeds.hackerdetection.service;

import com.example.hotelbeds.hackerdetection.service.impl.HackerDetectorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class HackerDetectorServiceTest {

    private HackerDetectorService hackerDetectorService;

    @BeforeEach
    public void setup() {
        hackerDetectorService = new HackerDetectorServiceImpl();
    }

    @Test
    void shouldGetHackerIpHappyPath() throws IOException {

        String fileName = "happy-path-test.log";
        String hackerIp = "185.205.226.2";

        String result = readLog(fileName);
        assertEquals(hackerIp, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "malformed-log-test.log",
            "malformed-sigin-enum-test.log",
            "malformed-IPv4-test.log",
            "malformed-epoch-test.log"
    })
    void shouldGetNullOnMalformedLogs(String fileName) throws IOException {
        String result = readLog(fileName);
        assertNull(result);
    }

    private String readLog(String fileName) throws IOException {

        Path resourceDirectory = Paths.get("src", "test", "resources", "logs");

        Stream<String> lines = Files.lines(Paths.get(resourceDirectory + "/" + fileName));

        return lines
                .map(line -> hackerDetectorService.parseLine(line))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }

}
