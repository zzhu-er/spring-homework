package com.example.springhomework.service;

import com.example.springhomework.model.User;
import com.example.springhomework.repository.UserRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserService subject;

  @Test
  void shouldReturnEmptyListWhenNoData() {
    //given
    when(userRepository.findAll()).thenReturn(Collections.emptyList());
    //when
    List<User> result = subject.findAll();
    //then
    Assertions.assertThat(result).isEqualTo(Collections.emptyList());
  }

  @Test
  void shouldReturnAllUsers() {
    //given
    List<User> expect = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    when(userRepository.findAll()).thenReturn(expect);
    //when
    List<User> result = subject.findAll();
    //then
    Assertions.assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetOnlyFirstUserInFirstPageWithPageSizeIs1() {
    //given
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    int page = 0;
    int size = 1;
    Pageable pageable = PageRequest.of(page, size);
    Page<User> expect = new PageImpl<>(userList, pageable, size);
    when(userRepository.findAll(pageable)).thenReturn(expect);
    //when
    List<User> result = subject.findAll(page, size);
    //then
    Assertions.assertThat(result).isEqualTo(expect.getContent());
  }

  @Test
  void shouldReturnAllUsersWithAge18WithoutPagination() {
    //given
    Long age = 18L;
    List<User> expect = asList(
        User.builder().id(0L).age(age).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(age).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    when(userRepository.findByAge(age)).thenReturn(expect);
    //when
    List<User> result = subject.findByAge(age);
    //then
    Assertions.assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetOnlyFirstUserWithAge18InFirstPageWithPageSizeIs1() {
    //given
    Long age = 18L;
    List<User> userList = asList(
        User.builder().id(0L).age(age).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(age).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    int page = 0;
    int size = 1;
    Pageable pageable = PageRequest.of(page, size);
    Page<User> expect = new PageImpl<>(userList, pageable, size);
    when(userRepository.findByAge(pageable, age)).thenReturn(expect);
    //when
    List<User> result = subject.findByAge(page, size, age);
    //then
    Assertions.assertThat(result).isEqualTo(expect.getContent());
  }

  @Test
  void shouldReturnAllUsersWithNameAWithoutPagination() {
    //given
    String name = "A";
    List<User> expect = asList(
        User.builder().id(0L).age(18L).name(name).createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name(name).createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    when(userRepository.findByName(name)).thenReturn(expect);
    //when
    List<User> result = subject.findByName(name);
    //then
    Assertions.assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetOnlyFirstUserWithNameAInFirstPageWithPageSizeIs1() {
    //given
    String name = "A";
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name(name).createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name(name).createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    int page = 0;
    int size = 1;
    Pageable pageable = PageRequest.of(page, size);
    Page<User> expect = new PageImpl<>(userList, pageable, size);
    when(userRepository.findByName(pageable, name)).thenReturn(expect);
    //when
    List<User> result = subject.findByName(page, size, name);
    //then
    Assertions.assertThat(result).isEqualTo(expect.getContent());
  }

  @Test
  void shouldReturnAllUsersBetweenMar7thAndMar9thWithoutPagination() {
    //given
    Instant instant = LocalDate.parse("2023-03-08").atStartOfDay(ZoneId.of("UTC")).toInstant();
    List<User> expect = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(instant).updatedAt(instant)
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(instant).updatedAt(instant)
            .build()
    );
    Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
    Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
    when(userRepository.findByCreatedAtBetween(start, end)).thenReturn(expect);
    //when
    List<User> result = subject.findAllBetweenDates(start, end);
    //then
    Assertions.assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetOnlyFirstUserBetweenMar7thAndMar9thInFirstPageWithPageSizeIs1() {
    //given
    Instant instant = LocalDate.parse("2023-03-08").atStartOfDay(ZoneId.of("UTC")).toInstant();
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(instant).updatedAt(instant)
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(instant).updatedAt(instant)
            .build()
    );
    int page = 0;
    int size = 1;
    Pageable pageable = PageRequest.of(page, size);
    Page<User> expect = new PageImpl<>(userList, pageable, size);
    Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
    Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
    when(userRepository.findByCreatedAtBetween(pageable, start, end)).thenReturn(expect);
    //when
    List<User> result = subject.findAllBetweenDates(page, size, start, end);
    //then
    Assertions.assertThat(result).isEqualTo(expect.getContent());
  }

  @Test
  void shouldSaveSuccessfully() {
    //given
    User savedUser = User.builder().id(0L).age(18L).name("A").createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
    when(userRepository.save(savedUser)).thenReturn(savedUser);
    //when
    subject.save(savedUser);
    //then
    verify(userRepository, times(1)).save(savedUser);
  }

  @Test
  void shouldCallDeleteMethodOnce() {
    //given
    User deletedUser = User.builder().id(1L).build();
    doNothing().when(userRepository).delete(deletedUser);
    //when
    subject.delete(deletedUser);
    //then
    verify(userRepository, times(1)).delete(deletedUser);
  }

  @Test
  void shouldUpdateUserSuccessfully() {
    //given
    Long id = 1L;
    User updatedUser = User.builder().id(id).name("UPDATE").age(100L).build();
    when(userRepository.save(updatedUser)).thenReturn(updatedUser);
    //when
    subject.update(updatedUser);
    //then
    verify(userRepository, times(1)).save(updatedUser);
  }
}
