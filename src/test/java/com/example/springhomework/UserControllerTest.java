package com.example.springhomework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    void ShouldGetEmptyWhenNoData() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptyList());

        mvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void ShouldGetSuccessWhenSaveUserSuccessfully() throws Exception {
//        User savedUser = User.builder().name("A").age(18L).build();
        User savedUser = new User();
        savedUser.setName("A");
        savedUser.setAge(18L);

        doNothing().when(userService).save(savedUser);

        mvc.perform(MockMvcRequestBuilders.post("/users").
                content(new ObjectMapper().writeValueAsString(savedUser)).
                contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("USER SAVED SUCCESSFULLY"));
    }
    @Test
    void ShouldGetSuccessWhenOneUserDeleted() throws Exception {
        User deletedUser = new User();
        deletedUser.setId(1L);

        mvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("USER DELETED SUCCESSFULLY"));

        verify(userService, times(1)).delete(refEq(deletedUser));
    }

    @Test
    void ShouldGetSuccessWhenUserUpdatedSuccessfully() throws Exception {
        User updatedUser = User.builder().name("UPDATE").age(100L).build();

        doNothing().when(userService).update(refEq(updatedUser));

        mvc.perform(MockMvcRequestBuilders.put("/users/1")
                    .content(new ObjectMapper().writeValueAsString(updatedUser))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("USER UPDATED SUCCESSFULLY"));
    }
}
