package com.example.hotelbeds.log.controller;

import com.example.hotelbeds.log.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs/v1")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/generate-random-log-file/{hacker-attempt-probability}")
    public ResponseEntity<String> generateRandomLogfile(@PathVariable(value = "hacker-attempt-probability") int hackerAttemptProbability) {
        return new ResponseEntity<>(logService.generateRandomLogfile(hackerAttemptProbability), HttpStatus.CREATED);
    }

}
