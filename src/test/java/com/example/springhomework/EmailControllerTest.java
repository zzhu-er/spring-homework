package com.example.springhomework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmailControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmailClient emailClient;
    @Test
    void shouldCallEmailClientGetByIdSuccessfully() throws Exception {
        Long userId = 1L;
        when(emailClient.getById(userId)).thenReturn(Collections.emptyList());

        mvc.perform(MockMvcRequestBuilders.get("/emails/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
        verify(emailClient, times(1)).getById(1L);
    }
}
