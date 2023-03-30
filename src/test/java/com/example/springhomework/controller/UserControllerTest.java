package com.example.springhomework.controller;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springhomework.dto.Email;
import com.example.springhomework.dto.UserRequest;
import com.example.springhomework.model.User;
import com.example.springhomework.service.UserService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @Mock
  private UserService userService;
  @InjectMocks
  private UserController subject;

  @Test
  void shouldGetAllUsersWhoseNameIsBAndAgeIs18AndWithoutPagination() {
    //given
    String name = "B";
    Long age = 18L;
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(18L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(2L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    List<User> expect = userList.subList(1, 2);
    when(userService.findAllDynamically(age, name, null, null)).thenReturn(expect);
    //when
    List<User> result = subject.queryAllDynamically(null, null, age, name, null, null);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersWhoseNameIsBAndAgeIs18WhenOnlyPageIsNull() {
    //given
    String name = "B";
    Long age = 18L;
    int size = 1;
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(18L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(2L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    List<User> expect = userList.subList(1, 2);
    when(userService.findAllDynamically(age, name, null, null)).thenReturn(expect);
    //when
    List<User> result = subject.queryAllDynamically(null, size, age, name, null, null);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersWhoseCreatedDateIsBetween2023Mar7thAndMar9thWhenOnlySizeIsNull() {
    //given
    Instant instant = LocalDate.parse("2023-03-08").atStartOfDay(ZoneId.of("UTC")).toInstant();
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(instant).updatedAt(instant).build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
    );
    Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
    Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
    int page = 0;
    List<User> expect = userList.subList(0, 1);
    when(userService.findAllDynamically(null, null, start, end)).thenReturn(expect);
    //when
    List<User> result = subject.queryAllDynamically(page, null, null, null, start, end);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetOnlyFirstUserInFirstPageWhoseNameIsBAndAgeIs18() {
    //given
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(18L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(2L).age(18L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(3L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    int page = 0;
    int size = 1;
    Long age = 18L;
    String name = "B";
    List<User> expect = userList.subList(1, 2);
    when(userService.findAllDynamicallyWithPagination(page, size, age, name, null, null)).thenReturn(expect);
    //when
    List<User> result = subject.queryAllDynamically(page, size, age, name, null, null);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetOnlyFirstUserInFirstPageWhoseCreatedDateIsBetween2023Mar7thAndMar9thAndNameIsBAndAgeIs18() {
    //given
    Instant instant = LocalDate.parse("2023-03-08").atStartOfDay(ZoneId.of("UTC")).toInstant();
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(instant).updatedAt(instant).build(),
        User.builder().id(1L).age(19L).name("A").createdAt(instant).updatedAt(instant).build(),
        User.builder().id(2L).age(18L).name("B").createdAt(instant).updatedAt(instant).build(),
        User.builder().id(3L).age(19L).name("B").createdAt(instant).updatedAt(instant).build(),
        User.builder().id(4L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(5L).age(19L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(6L).age(18L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(7L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(8L).age(18L).name("B").createdAt(instant).updatedAt(instant).build()
    );
    Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
    Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
    int page = 0;
    int size = 1;
    Long age = 18L;
    String name = "B";
    List<User> expect = userList.subList(2, 3);
    when(userService.findAllDynamicallyWithPagination(page, size, age, name, start, end)).thenReturn(expect);
    //when
    List<User> result = subject.queryAllDynamically(page, size, age, name, start, end);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetSuccessWhenSaveUserSuccessfully() throws Exception {
    //given
    List<Email> emailList = List.of(new Email("A@thoughtworks.com"));
    UserRequest userRequest = new UserRequest("A", 18L, emailList);
    ResponseEntity<String> expect = new ResponseEntity<>("USER SAVED SUCCESSFULLY",
        HttpStatus.CREATED);
    when(userService.save(userRequest)).thenReturn(expect);
    //when
    ResponseEntity<String> result = subject.save(userRequest);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetSuccessWhenOneUserDeleted() {
    //given
    Long id = 1L;
    User deletedUser = new User();
    deletedUser.setId(id);
    doNothing().when(userService).delete(deletedUser);
    //when
    ResponseEntity<String> result = subject.delete(id);
    //then
    verify(userService, times(1)).delete(deletedUser);
    assertThat(result).isEqualTo(new ResponseEntity<>("USER DELETED SUCCESSFULLY", HttpStatus.OK));
  }

  @Test
  void shouldGetSuccessWhenUserUpdatedSuccessfully() {
    //given
    Long id = 1L;
    String name = "UPDATE";
    Long age = 100L;
    User updatedUser = User.builder().name(name).age(age).build();
    doNothing().when(userService).update(updatedUser);
    //when
    ResponseEntity<String> result = subject.update(id, updatedUser);
    //then
    verify(userService, times(1)).update(updatedUser);
    assertThat(updatedUser.getId()).isEqualTo(id);
    assertThat(result).isEqualTo(new ResponseEntity<>("USER UPDATED SUCCESSFULLY", HttpStatus.OK));
  }

  @Test
  void shouldCallEmailClientGetByIdSuccessfully() throws Exception {
    //given
    Long userId = 1L;
    List<Email> expect = Collections.emptyList();
    when(userService.getEmailsByUserId(userId)).thenReturn(expect);
    //when
    List<Email> result = subject.getEmailsByUserId(userId);
    //then
    assertThat(result).isEqualTo(expect);
  }
}
