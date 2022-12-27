package com.example.hotelbeds.log.service.impl;

import com.example.hotelbeds.common.enums.SignInResult;
import com.example.hotelbeds.hackerdetection.service.HackerDetectorService;
import com.example.hotelbeds.log.service.LogService;
import com.example.hotelbeds.log.utils.EpochGenerator;
import com.example.hotelbeds.log.utils.IPv4Generator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import static com.example.hotelbeds.common.constants.LogConstants.*;

@Service
@Slf4j
@AllArgsConstructor
public class LogServiceImpl implements LogService {
    private HackerDetectorService hackerDetectorService;

    @Override
    public String generateRandomLogfile(int hackerAttemptProbability) {

        String fileName = createLogFileName();

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(fileName), StandardCharsets.UTF_8)) {

            generateOver100KLogs(hackerAttemptProbability, writer);

        } catch (IOException e) {
            log.error("Error on generating new Logfile: " + e.getMessage());
        }
        return fileName;
    }

    @Override
    public void readLogFile(String logFilename) {


        try (Stream<String> logLines = Files.lines(Paths.get(LOG_MONITORING_DIRECTORY + logFilename))) {

            readLogLines(logLines);
            hackerDetectorService.clearHackerAttempts();

        } catch (IOException e) {
            log.error("LogFile not found:  " + logFilename);
        }
    }

    private static String createLogFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_LOG_FILE_PATTERN);
        return LOG_DIRECTORY + simpleDateFormat.format(date) + LOG_EXTENSION;
    }

    private static void generateOver100KLogs(int hackerAttemptProbability, BufferedWriter writer) throws IOException {

        for (int logNum = 0; logNum <= MINIMUM_AMOUNT_OF_LOGS; logNum++) {

            if (hackerAttemptLogIsLikely(hackerAttemptProbability)) {

                generateHackingAttack(writer, logNum);

            } else {
                writeLog(writer, generateSuccesfulLog(logNum));
            }

        }
    }

    private static boolean hackerAttemptLogIsLikely(int probability) {
        Random random = new Random();
        return random.nextInt(100) < probability;
    }

    private static void generateHackingAttack(Writer writer, int recordNum) throws IOException {

        String hackerIpv4 = IPv4Generator.generateRandomIPv4();

        for (int hackerLogNum = 0; hackerLogNum < 5; hackerLogNum++) {
            writeLog(writer, generateHackingAttackLog(recordNum, hackerIpv4));
        }

    }

    private static String generateHackingAttackLog(int recordNum, String hackerIpv4) {

        return
                hackerIpv4
                        + SEPARATOR + EpochGenerator.generateEpoch(recordNum)
                        + SEPARATOR + SignInResult.SIGNIN_FAILURE
                        + SEPARATOR + "Bad.Hacker";
    }

    private static String generateSuccesfulLog(int recordNum) {
        return
                IPv4Generator.generateRandomIPv4()
                        + SEPARATOR + EpochGenerator.generateEpoch(recordNum)
                        + SEPARATOR + SignInResult.SIGNIN_SUCCESS
                        + SEPARATOR + "David.Larena";
    }

    private static void writeLog(Writer writer, String logRecord) throws IOException {
        writer.write(logRecord);
        writer.write('\n');
    }

    private void readLogLines(Stream<String> logLines) {
        logLines.forEach(logString -> {
            String ip = hackerDetectorService.parseLine(logString);
            if (ip != null)
                log.info("Attempted Hacker Attack :: " + ip);
        });
    }


}
