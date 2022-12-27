package com.example.hotelbeds.log.controller;

import com.example.hotelbeds.log.service.LogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogController.class)
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService logService;

    @Test
    void testPostMethod() throws Exception {
        when(logService.generateRandomLogfile(1)).thenReturn("logs/tmp/generatedLog.log");
        mockMvc.perform(post("/logs/v1/generate-random-log-file/{hacker-attempt-probability}", 1))
                .andExpect(status().isCreated());
    }

}
