package com.example.springhomework.controller;

import com.example.springhomework.dto.Email;
import com.example.springhomework.service.EmailClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailControllerTest {

    @Mock
    private EmailClient emailClient;

    @InjectMocks
    private EmailController subject;

    @Test
    void shouldCallEmailClientGetByIdSuccessfully() throws Exception {
        //given
        Long userId = 1L;
        List<Email> expect = Collections.emptyList();
        when(emailClient.getById(userId)).thenReturn(expect);
        //when
        List<Email> result = subject.getByUserId(userId);
        //then
        assertThat(result).isEqualTo(expect);
    }
}
