package com.example.practice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class PracticeControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PracticeController mockPracticeController;

    @Test
    public void getName_ReturnName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/practice"))
                .andReturn()
                .getResponse();

        verify(mockPracticeController).getAllNames();
    }
}
