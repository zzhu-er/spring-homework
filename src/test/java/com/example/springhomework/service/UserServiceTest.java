package com.example.springhomework.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springhomework.dto.Email;
import com.example.springhomework.dto.UserRequest;
import com.example.springhomework.model.User;
import com.example.springhomework.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private EmailClient emailClient;
  @InjectMocks
  private UserService subject;

  @Test
  void shouldSaveSuccessfully() throws Exception {
    //given
    List<Email> emailList = List.of(new Email("test@thoughtworks.com"));
    UserRequest userRequest = new UserRequest("A", 18L, emailList);
    User user = User.builder().age(userRequest.getAge()).name(userRequest.getName()).build();
    User savedUser = User.builder().id(1L).age(user.getAge()).name(user.getName())
        .createdAt(Instant.now())
        .updatedAt(Instant.now()).build();
    when(userRepository.save(user)).thenReturn(savedUser);
    ResponseEntity<String> expect = new ResponseEntity<>("USER SAVED SUCCESSFULLY",
        HttpStatus.CREATED);
    //when
    ResponseEntity<String> result = subject.save(userRequest);
    //then
    verify(emailClient, times(1)).saveEmail(savedUser.getId(), emailList);
    Assertions.assertThat(result).isEqualTo(expect);
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

  @Test
  void shouldGetEmailListCorrespondingToASpecificUserId() throws Exception {
    //given
    Long userId = 1L;
    List<Email> expect = List.of(new Email("test@thoughtworks.com"));
    when(emailClient.getById(userId)).thenReturn(expect);
    //when
    List<Email> result = subject.getEmailsByUserId(userId);
    //then
    Assertions.assertThat(result).isEqualTo(expect);
  }

  @Test
  void shouldGetUserWhoseIdIsOne() {
    //given
    Long id = 1L;
    Optional<User> expect = Optional.of(User.builder().id(1L).age(18L).name("A").build());
    when(userRepository.findById(id)).thenReturn(expect);
    //when
    Optional<User> result = subject.getUserById(id);
    //then
    Assertions.assertThat(result).isEqualTo(expect);
  }
}
