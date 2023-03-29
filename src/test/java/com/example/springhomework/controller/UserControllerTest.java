package com.example.springhomework.controller;

import com.example.springhomework.dto.Email;
import com.example.springhomework.dto.UserRequest;
import com.example.springhomework.model.User;
import com.example.springhomework.service.EmailClient;
import com.example.springhomework.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @Mock
  private UserService userService;
  @InjectMocks
  private UserController subject;

  @Test
  void shouldGetAllUsersWithoutPagination() {
    //given
    List<User> expect = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    when(userService.findAll()).thenReturn(expect);
    //when
    List<User> result = subject.getAll(null, null);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersWhenOnlyPageIsNull() {
    //given
    List<User> expect = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    when(userService.findAll()).thenReturn(expect);
    //when
    List<User> result = subject.getAll(null, 1);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersWhenOnlySizeIsNull() {
    //given
    List<User> expect = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    when(userService.findAll()).thenReturn(expect);
    //when
    List<User> result = subject.getAll(0, null);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetOnlyFirstUserInFirstPageWhenPageSizeIs1() {
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
    Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());
    List<User> expect = userPage.getContent().subList(0, 1);
    when(userService.findAll(page, size)).thenReturn(expect);
    //when
    List<User> result = subject.getAll(page, size);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetEmptyWhenNoData() {
    //given
    List<User> expect = Collections.emptyList();
    when(userService.findAll()).thenReturn(expect);
    //when
    List<User> result = subject.getAll(null, null);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersWithAge18WithoutPagination() {
    //given
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    Long age = 18L;
    List<User> expect = userList.subList(0, 1);
    when(userService.findByAge(age)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByAge(null, null, age);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersWithAge18WhenOnlyPageIsNull() {
    //given
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    Long age = 18L;
    List<User> expect = userList.subList(0, 1);
    when(userService.findByAge(age)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByAge(null, 1, age);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersWithAge18WhenOnlySizeIsNull() {
    //given
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    Long age = 18L;
    List<User> expect = userList.subList(0, 1);
    when(userService.findByAge(age)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByAge(0, null, age);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetOnlyFirstUserWithAge18InFirstPageWhenPageSizeIs1() {
    //given
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(18L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    Long age = 18L;
    int page = 0;
    int size = 1;
    Pageable pageable = PageRequest.of(page, size);
    Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());
    List<User> expect = userPage.getContent().subList(0, 1);
    when(userService.findByAge(page, size, age)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByAge(page, size, age);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersWithNameAWithoutPagination() {
    //given
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    String name = "A";
    List<User> expect = userList.subList(0, 1);
    when(userService.findByName(name)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByName(null, null, name);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersWithNameAWhenOnlyPageIsNull() {
    //given
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    String name = "A";
    List<User> expect = userList.subList(0, 1);
    when(userService.findByName(name)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByName(null, 1, name);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersWithNameAWhenOnlySizeIsNull() {
    //given
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    String name = "A";
    List<User> expect = userList.subList(0, 1);
    when(userService.findByName(name)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByName(0, null, name);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetOnlyFirstUserWithNameAInFirstPageWhenPageSizeIs1() {
    //given
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build(),
        User.builder().id(1L).age(19L).name("A").createdAt(Instant.now()).updatedAt(Instant.now())
            .build()
    );
    int page = 0;
    int size = 1;
    String name = "A";
    Pageable pageable = PageRequest.of(page, size);
    Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());
    List<User> expect = userPage.getContent().subList(0, 1);
    when(userService.findByName(page, size, name)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByName(page, size, name);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersBetweenMar7thAndMar9thWithoutPagination() {
    //given
    Instant instant = LocalDate.parse("2023-03-08").atStartOfDay(ZoneId.of("UTC")).toInstant();
    List<User> expect = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(instant).updatedAt(instant).build(),
        User.builder().id(1L).age(19L).name("B").createdAt(instant).updatedAt(instant).build()
    );
    Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
    Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
    when(userService.findAllBetweenDates(start, end)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByTime(null, null, start, end);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersBetweenMar7thAndMar9thWhenOnlyPageIsNull() {
    //given
    Instant instant = LocalDate.parse("2023-03-08").atStartOfDay(ZoneId.of("UTC")).toInstant();
    List<User> expect = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(instant).updatedAt(instant).build(),
        User.builder().id(1L).age(19L).name("B").createdAt(instant).updatedAt(instant).build()
    );
    Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
    Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
    when(userService.findAllBetweenDates(start, end)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByTime(null, 1, start, end);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetAllUsersBetweenMar7thAndMar9thWhenOnlySizeIsNull() {
    //given
    Instant instant = LocalDate.parse("2023-03-08").atStartOfDay(ZoneId.of("UTC")).toInstant();
    List<User> expect = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(instant).updatedAt(instant).build(),
        User.builder().id(1L).age(19L).name("B").createdAt(instant).updatedAt(instant).build()
    );
    Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
    Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
    when(userService.findAllBetweenDates(start, end)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByTime(0, null, start, end);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetOnlyFirstUserBetweenMar7thAndMar9thInFirstPageWhenPageSizeIs1() {
    //given
    Instant instant = LocalDate.parse("2023-03-08").atStartOfDay(ZoneId.of("UTC")).toInstant();
    List<User> userList = asList(
        User.builder().id(0L).age(18L).name("A").createdAt(instant).updatedAt(instant).build(),
        User.builder().id(1L).age(19L).name("B").createdAt(instant).updatedAt(instant).build()
    );
    Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
    Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
    int page = 0;
    int size = 1;
    Pageable pageable = PageRequest.of(page, size);
    Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());
    List<User> expect = userPage.getContent().subList(0, 1);
    when(userService.findAllBetweenDates(page, size, start, end)).thenReturn(expect);
    //when
    List<User> result = subject.getAllByTime(page, size, start, end);
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
  void shouldGetSuccessWhenUserUpdatedSuccessfully() throws Exception {
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
