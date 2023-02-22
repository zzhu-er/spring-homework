package com.example.springhomework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    @Test
    void ShouldGetAllUsers() throws Exception {
        List<User> response = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );

        when(userService.findAll()).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].age", is(18)))
                .andExpect(jsonPath("$[0].name", is("A")));
    }

}
