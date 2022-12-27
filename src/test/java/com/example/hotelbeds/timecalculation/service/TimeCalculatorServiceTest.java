package com.example.hotelbeds.timecalculation.service;

import com.example.hotelbeds.timecalculation.RFC1123Timestamps;
import com.example.hotelbeds.timecalculation.exceptions.RFC1123ParsingException;
import com.example.hotelbeds.timecalculation.service.impl.TimeCalculatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
class TimeCalculatorServiceTest {

    private TimeCalculatorService timeCalculatorService;

    private static final String FIRST_RFC_2822_DATE = "26 Dec 2022 17:30:00 GMT";
    private static final String SECOND_RFC_2822_DATE = "26 Dec 2022 17:15:00 GMT";
    private static final String SECOND_RFC_2822_DATE_NOT_ROUNDED = "26 Dec 2022 17:15:45 GMT";
    private static final String DIFFERENT_TIMEZONED_RFC_2822 = "26 Dec 2022 17:15:45 +0100";

    private static RFC1123Timestamps RFC_1123_TIMESTAMPS;
    @BeforeEach
    public void setup() {
        timeCalculatorService = new TimeCalculatorServiceImpl();
        RFC_1123_TIMESTAMPS = new RFC1123Timestamps();
    }

    @Test
    void shouldReturn15MinRounded() throws RFC1123ParsingException {
        RFC_1123_TIMESTAMPS.setTimeStamp1(FIRST_RFC_2822_DATE);
        RFC_1123_TIMESTAMPS.setTimeStamp2(SECOND_RFC_2822_DATE);

        long minutesBetweenEpochs = timeCalculatorService.getMinutesBetweenTimestamps(RFC_1123_TIMESTAMPS);
        assertEquals(15L,minutesBetweenEpochs);
    }

    @Test
    void shouldReturn14MinRounded() throws RFC1123ParsingException {
        RFC_1123_TIMESTAMPS.setTimeStamp1(FIRST_RFC_2822_DATE);
        RFC_1123_TIMESTAMPS.setTimeStamp2(SECOND_RFC_2822_DATE_NOT_ROUNDED);

        long minutesBetweenEpochs = timeCalculatorService.getMinutesBetweenTimestamps(RFC_1123_TIMESTAMPS);
        assertEquals(14L,minutesBetweenEpochs);
    }

    @Test
    void shouldReturn74MinDifferentTimezone() throws RFC1123ParsingException {
        RFC_1123_TIMESTAMPS.setTimeStamp1(FIRST_RFC_2822_DATE);
        RFC_1123_TIMESTAMPS.setTimeStamp2(DIFFERENT_TIMEZONED_RFC_2822);

        long minutesBetweenEpochs = timeCalculatorService.getMinutesBetweenTimestamps(RFC_1123_TIMESTAMPS);
        assertEquals(74L,minutesBetweenEpochs);
    }

    @Test
    void shouldThrowRFC1123ParsingException() {
        assertThrows(RFC1123ParsingException.class, () -> timeCalculatorService.getMinutesBetweenTimestamps(new RFC1123Timestamps("","")));
    }

}
