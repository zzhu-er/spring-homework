package com.example.springhomework.service;

import com.example.springhomework.dto.Email;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmailFallbackTest {

  private EmailFallback subject;

  @BeforeEach
  void setUp() {
    subject = new EmailFallback();
  }

  @Test
  void shouldReturnEmptyListWhenGetById() {
    //given
    Long userId = 1L;
    //when
    List<Email> result = subject.getById(userId);
    //then
    Assertions.assertThat(result).isEqualTo(Collections.emptyList());
  }

  @Test
  void shouldThrowRunTimeExceptionWhenSaveEmail() {
    //given
    Long userId = 1L;
    List<Email> emailList = List.of(new Email());
    //when & then
    Assertions.assertThatThrownBy(() -> subject.saveEmail(userId, emailList))
        .isInstanceOf(RuntimeException.class);
  }
}
