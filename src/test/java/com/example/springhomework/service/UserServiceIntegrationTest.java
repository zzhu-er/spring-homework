package com.example.springhomework.service;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.allOf;

import com.example.springhomework.dto.PageResponse;
import com.example.springhomework.dto.UserResponse;
import com.example.springhomework.model.User;
import com.example.springhomework.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserServiceIntegrationTest {

  @Autowired
  private UserRepository userRepository;
  private final EmailClient emailClient = Mockito.mock(EmailClient.class);
  private UserService subject;

  @BeforeEach
  void setUp() {
    List<User> userList = asList(
        User.builder().age(18L).name("A").build(),
        User.builder().age(19L).name("A").build(),
        User.builder().age(18L).name("B").build(),
        User.builder().age(19L).name("B").build(),
        User.builder().age(18L).name("A").build(),
        User.builder().age(19L).name("A").build(),
        User.builder().age(18L).name("B").build(),
        User.builder().age(19L).name("B").build(),
        User.builder().age(18L).name("B").build()
    );
    userRepository.saveAll(userList);
    subject = new UserService(userRepository, emailClient);
  }

  @Test
  void shouldGetExpectedUsersWithPagination() {
    //given
    Long age = 18L;
    String name = "B";
    Integer page = 0;
    Integer size = 10;
    //when
    PageResponse<UserResponse> result = subject.findAllDynamicallyWithPagination(page, size,
        age, name, null, null);
    //then
    UserResponse subResponse = result.getContent();
    Condition<User> eighteen = new Condition<>(user -> user.getAge() == 18L, "age 18");
    Condition<User> nameB = new Condition<>(user -> user.getName().equals("B"), "name B");
    Assertions.assertThat(subResponse.getContent()).hasSize(3);
    Assertions.assertThat(subResponse.getContent()).haveExactly(3, allOf(eighteen, nameB));
    Assertions.assertThat(result.getTotalPages()).isEqualTo(1);
    Assertions.assertThat(result.getTotalElements()).isEqualTo(3L);
    Assertions.assertThat(result.getCurrentPage()).isEqualTo(0);
  }

  @Test
  void shouldGetEmptyUserWhoseCreateDateIsAfterTomorrow() {
    //given
    Instant tomorrow = Instant.now().plusSeconds(24 * 60 * 60);
    //when
    PageResponse<UserResponse> result = subject.findAllDynamicallyWithPagination(0, 5, null,
        null, tomorrow, null);
    //then
    Assertions.assertThat(result.getTotalElements()).isEqualTo(0);
  }

  @Test
  void shouldGetEmptyUserWhoseCreateDateIsBeforeYesterday() {
    //given
    Instant yesterday = Instant.now().minusSeconds(24 * 60 * 60);
    //when
    PageResponse<UserResponse> result = subject.findAllDynamicallyWithPagination(
        0, 5, null, null, null, yesterday);
    //then
    Assertions.assertThat(result.getTotalElements()).isEqualTo(0);
  }

  @Test
  void shouldGetUsersWhoseIdsAreTwoAndSixInFirstPageWithFilteringByNameAndAge() {
    //given
    String name = "A";
    Long age = 19L;
    int page = 0;
    int size = 2;
    Instant yesterday = Instant.now().minusSeconds(24 * 60 * 60);
    Instant tomorrow = Instant.now().plusSeconds(24 * 60 * 60);
    //when
    PageResponse<UserResponse> result = subject.findAllDynamicallyWithPagination(
        page, size, age, name, yesterday, tomorrow);
    List<User> userContent = result.getContent().getContent();
    //then
    Assertions.assertThat(result.getTotalPages()).isEqualTo(1);
    Assertions.assertThat(result.getCurrentPage()).isEqualTo(0);
    Assertions.assertThat(result.getTotalElements()).isEqualTo(2);
    Assertions.assertThat(userContent.get(0).getId()).isEqualTo(2);
    Assertions.assertThat(userContent.get(1).getId()).isEqualTo(6);
  }
}