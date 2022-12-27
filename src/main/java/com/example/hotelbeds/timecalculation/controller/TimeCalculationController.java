package com.example.hotelbeds.timecalculation.controller;

import com.example.hotelbeds.timecalculation.RFC1123Timestamps;
import com.example.hotelbeds.timecalculation.exceptions.RFC1123ParsingException;
import com.example.hotelbeds.timecalculation.service.TimeCalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/time-calculations/v1")
public class TimeCalculationController {

    private final TimeCalculatorService timeCalculatorService;

    public TimeCalculationController(TimeCalculatorService timeCalculatorService) {
        this.timeCalculatorService = timeCalculatorService;
    }

    @PostMapping("/minutes-between-timestamps")
    public ResponseEntity<Long> getMinutesBetweenTimestamps(@RequestBody RFC1123Timestamps rfc1123Timestamps) throws RFC1123ParsingException {
        return ResponseEntity.ok(timeCalculatorService.getMinutesBetweenTimestamps(rfc1123Timestamps));
    }

}
