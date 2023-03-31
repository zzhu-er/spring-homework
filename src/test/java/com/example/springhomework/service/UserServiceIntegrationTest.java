package com.example.springhomework.service;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.allOf;

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
  private UserService userService;

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
    userService = new UserService(userRepository, emailClient);
  }

  @Test
  void shouldGetUsersWhoseNameIsBAndAgeIs18() {
    //given
    Long age = 18L;
    String name = "B";
    //when
    List<User> result = userService.findAllDynamically(age, name, null, null);
    //then
    Condition<User> eighteen = new Condition<>(user -> user.getAge() == 18L, "age 18");
    Condition<User> nameB = new Condition<>(user -> user.getName().equals("B"), "name B");
    Assertions.assertThat(result).hasSize(3);
    Assertions.assertThat(result).haveExactly(3, allOf(eighteen, nameB));
  }

  @Test
  void shouldGetEmptyUserWhoseCreateDateIsAfterTomorrow() {
    //given
    Instant tomorrow = Instant.now().plusSeconds(24 * 60 * 60);
    //when
    List<User> result = userService.findAllDynamically(null, null, tomorrow, null);
    //then
    Assertions.assertThat(result).isEmpty();
  }

  @Test
  void shouldGetEmptyUserWhoseCreateDateIsBeforeYesterday() {
    //given
    Instant yesterday = Instant.now().minusSeconds(24 * 60 * 60);
    //when
    List<User> result = userService.findAllDynamically(null, null, null, yesterday);
    //then
    Assertions.assertThat(result).isEmpty();
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
    List<User> result = userService.findAllDynamicallyWithPagination(page, size, age, name,
        yesterday, tomorrow);
    //then
    Assertions.assertThat(result).hasSize(2);
    Assertions.assertThat(result.get(0).getId()).isEqualTo(2);
    Assertions.assertThat(result.get(1).getId()).isEqualTo(6);
  }
}