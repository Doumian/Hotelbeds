package com.example.hotelbeds.timecalculation.controller;

import com.example.hotelbeds.timecalculation.RFC1123Timestamps;
import com.example.hotelbeds.timecalculation.service.TimeCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimeCalculationController.class)
class TimeCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeCalculatorService timeCalculatorService;

    @Test
    void testPostMethod() throws Exception {
        when(timeCalculatorService.getMinutesBetweenTimestamps(new RFC1123Timestamps("", ""))).thenReturn(5L);
        mockMvc.perform(post("/time-calculations/v1/minutes-between-timestamps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"timeStamp1\":\"26 Dec 2022 17:30:00 GMT\",\"timeStamp2\":\"26 Dec 2022 17:15:00 GMT\"}"))
                .andExpect(status().isOk());

    }

}
