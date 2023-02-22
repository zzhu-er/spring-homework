package com.example.springhomework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    void ShouldReturnEmptyListWhenNoData() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> allUsers = userService.findAll();

        assertNotNull(allUsers);
        assertEquals(0, allUsers.size());
    }

    @Test
    void ShouldReturnAllUsers() {
        List<User> mockedUsers = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );

        when(userRepository.findAll()).thenReturn(mockedUsers);

        List<User> allUsers = userService.findAll();

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertEquals(0, allUsers.get(0).getId());
        assertEquals(1, allUsers.get(1).getId());
        assertEquals(18, allUsers.get(0).getAge());
        assertEquals(19, allUsers.get(1).getAge());
        assertEquals("A", allUsers.get(0).getName());
        assertEquals("B", allUsers.get(1).getName());
    }
}
