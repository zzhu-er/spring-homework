package com.example.springhomework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private EmailClient emailClient;

    @Test
    void shouldGetAllUsersWithoutPagination() throws Exception {
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
        verify(userService, times(1)).findAll();
    }

    @Test
    void shouldGetOnlyFirstUserInFirstPageWhenPageSizeIs1() throws Exception {
        List<User> response = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> page = new PageImpl<>(response, pageable, response.size());
        when(userService.findAll(0, 1)).thenReturn(page.getContent().subList(0,1));

        mvc.perform(MockMvcRequestBuilders.get("/users?page=0&size=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].age", is(18)))
                .andExpect(jsonPath("$[0].name", is("A")));
        verify(userService, times(1)).findAll(0, 1);
    }

    @Test
    void shouldGetEmptyWhenNoData() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptyList());

        mvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void shouldGetAllUsersWithAge18() throws Exception {
        List<User> response = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );

        when(userService.findByAge(18L)).thenReturn(response.subList(0,1));

        mvc.perform(MockMvcRequestBuilders.get("/users/?age=18").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].age", is(18)))
                .andExpect(jsonPath("$[0].name", is("A")));
        verify(userService, times(1)).findByAge(18L);
    }

    @Test
    void shouldGetOnlyFirstUserWithAge18InFirstPageWhenPageSizeIs1() throws Exception {
        List<User> response = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> page = new PageImpl<>(response, pageable, response.size());
        when(userService.findByAge(0, 1, 18L)).thenReturn(page.getContent().subList(0,1));

        mvc.perform(MockMvcRequestBuilders.get("/users/?page=0&size=1&age=18").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].age", is(18)))
                .andExpect(jsonPath("$[0].name", is("A")));
        verify(userService, times(1)).findByAge(0, 1, 18L);
    }

    @Test
    void shouldGetAllUsersWithNameA() throws Exception {
        List<User> response = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );

        when(userService.findByName("A")).thenReturn(response.subList(0,1));

        mvc.perform(MockMvcRequestBuilders.get("/users/?name=A").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].age", is(18)))
                .andExpect(jsonPath("$[0].name", is("A")));
        verify(userService, times(0)).findByName(0, 1, "A");
        verify(userService, times(1)).findByName("A");
    }

    @Test
    void shouldGetOnlyFirstUserWithNameAInFirstPageWhenPageSizeIs1() throws Exception {
        List<User> response = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> page = new PageImpl<>(response, pageable, response.size());
        when(userService.findByName(0, 1, "A")).thenReturn(page.getContent().subList(0,1));

        mvc.perform(MockMvcRequestBuilders.get("/users/?page=0&size=1&name=A").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].age", is(18)))
                .andExpect(jsonPath("$[0].name", is("A")));
        verify(userService, times(1)).findByName(0, 1, "A");
        verify(userService, times(0)).findByName("A");
    }

    @Test
    void shouldGetAllUsersBetweenMar7thAndMar9th() throws Exception {
        List<User> response = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
        Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
        when(userService.findAllBetweenDates(start, end)).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/users/?from=" + start.toString() + "&to=" + end.toString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].age", is(18)))
                .andExpect(jsonPath("$[0].name", is("A")));
        verify(userService, times(0)).findAllBetweenDates(0, 1, start, end);
        verify(userService, times(1)).findAllBetweenDates(start, end);
    }

    @Test
    void shouldGetOnlyFirstUserBetweenMar7thAndMar9thInFirstPageWhenPageSizeIs1() throws Exception {
        List<User> response = asList(
                User.builder().id(0L).age(18L).name("A").createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                User.builder().id(1L).age(19L).name("B").createdAt(Instant.now()).updatedAt(Instant.now()).build()
        );
        Instant start = LocalDate.parse("2023-03-07").atStartOfDay(ZoneId.of("UTC")).toInstant();
        Instant end = LocalDate.parse("2023-03-09").atStartOfDay(ZoneId.of("UTC")).toInstant();
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> page = new PageImpl<>(response, pageable, response.size());
        when(userService.findAllBetweenDates(0, 1, start, end)).thenReturn(page.getContent().subList(0,1));

        mvc.perform(MockMvcRequestBuilders.get("/users/?page=0&size=1&from=" + start.toString() + "&to=" + end.toString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].age", is(18)))
                .andExpect(jsonPath("$[0].name", is("A")));
        verify(userService, times(1)).findAllBetweenDates(0, 1, start, end);
        verify(userService, times(0)).findAllBetweenDates(start, end);
    }

    @Test
    void shouldGetSuccessWhenSaveUserSuccessfully() throws Exception {
        User savedUser = new User();
        savedUser.setName("A");
        savedUser.setAge(18L);
        List<Email> emailList = List.of(new Email(1L, null, "A@thoughtworks.com"));
        UserRequest userRequest = new UserRequest(savedUser.getName(), savedUser.getAge(),emailList);
        doNothing().when(userService).save(savedUser);
        when(emailClient.saveEmail(1L, emailList)).thenReturn(new ResponseEntity<>("EMAIL SAVED SUCCESSFULLY", HttpStatus.CREATED));

        mvc.perform(MockMvcRequestBuilders.post("/users").
                content(new ObjectMapper().writeValueAsString(userRequest)).
                contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("USER SAVED SUCCESSFULLY"));
        verify(userService, times(1)).save(savedUser);
        verify(emailClient, times(1)).saveEmail(null, emailList);
    }
    @Test
    void shouldGetSuccessWhenOneUserDeleted() throws Exception {
        User deletedUser = new User();
        deletedUser.setId(1L);

        mvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("USER DELETED SUCCESSFULLY"));

        verify(userService, times(1)).delete(refEq(deletedUser));
    }

    @Test
    void shouldGetSuccessWhenUserUpdatedSuccessfully() throws Exception {
        User updatedUser = User.builder().name("UPDATE").age(100L).build();

        doNothing().when(userService).update(refEq(updatedUser));

        mvc.perform(MockMvcRequestBuilders.put("/users/1")
                    .content(new ObjectMapper().writeValueAsString(updatedUser))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("USER UPDATED SUCCESSFULLY"));
    }
}
