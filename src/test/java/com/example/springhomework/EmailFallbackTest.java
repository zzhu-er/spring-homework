package com.example.springhomework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmailFallbackTest {
    @Autowired
    private EmailFallback emailFallback;

    @Test
    void shouldReturnEmptyListWhenGetById() {
        Long userId = 1L;
        List<Email> result = emailFallback.getById(userId);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shouldThrowRunTimeExceptionWhenSaveEmail() {
        Long userId = 1L;
        List<Email> emailList = List.of(new Email());
        assertThrows(RuntimeException.class, ()->emailFallback.saveEmail(userId, emailList));
    }
}
