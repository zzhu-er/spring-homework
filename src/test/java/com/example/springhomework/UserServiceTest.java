package com.example.springhomework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

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
}
