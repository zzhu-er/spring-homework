package com.example.springhomework.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springhomework.dto.Email;
import com.example.springhomework.dto.PageResponse;
import com.example.springhomework.dto.UserRequest;
import com.example.springhomework.dto.UserResponse;
import com.example.springhomework.model.User;
import com.example.springhomework.service.UserService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @Mock
  private UserService userService;
  @InjectMocks
  private UserController subject;

  @Test
  void shouldGetExpectedResponseWhenSaveUserSuccessfully() throws Exception {
    //given
    List<Email> emails = new ArrayList<>();
    UserRequest userRequest = new UserRequest("name", 18L, emails);
    doNothing().when(userService).save(userRequest);
    //when
    ResponseEntity<String> result = subject.save(userRequest);
    //then
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(result.getBody()).isEqualTo("USER SAVED SUCCESSFULLY");
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
    List<Email> expect = new ArrayList<>();
    when(userService.getEmailsByUserId(userId)).thenReturn(expect);
    //when
    List<Email> result = subject.getEmailsByUserId(userId);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetUserWhoseIdIs1() {
    //given
    Long id = 1L;
    User expect = User.builder().id(1L).age(18L).name("A").build();
    when(userService.getUserById(id)).thenReturn(Optional.ofNullable(expect));
    //when
    User result = subject.getUserById(id);
    //then
    assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldThrowExceptionWhenUserNotExist() {
    //given
    Long id = 1L;
    when(userService.getUserById(id)).thenReturn(Optional.empty());
    //when
    ResponseStatusException caughtException = catchThrowableOfType(
        () -> subject.getUserById(id), ResponseStatusException.class);
    //then
    assertThat(caughtException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(caughtException.getReason()).isEqualTo(
        String.format("User with id %d not existed", id));
  }

  @Test
  void shouldReturnExpectedUserFromUserService() {
    //give
    int page = 0;
    int size = 10;
    long age = 18L;
    String name = "A";
    Instant startDate = Instant.now();
    Instant endDate = Instant.now();
    PageResponse<UserResponse> expect = new PageResponse<>();
    when(userService.findAllDynamicallyWithPagination(page, size, age, name, startDate,
        endDate)).thenReturn(expect);
    //when
    ResponseEntity<PageResponse<UserResponse>> result = subject.queryAllDynamically(page,
        size, age, name, startDate, endDate);
    //then
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isEqualTo(expect);
  }
}
