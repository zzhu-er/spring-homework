package com.example.springhomework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootTestUserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserController userController;

    @AfterEach
    void tearDown() {
        userController.deleteAll();
    }

//    @Test
//    void shouldGetEmptyUsers() {
//        ResponseEntity<List> response = restTemplate.getForEntity("/users", List.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
//        assertThat(response.getBody()).isEmpty();
//    }

//    @Test
//    void shouldGetAllUsers() {
//        List<User> data = asList(
//                User.builder().id(0L).age(18L).name("A").build(),
//                User.builder().id(1L).age(19L).name("B").build()
//        );
//        userController.save(data);
//
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        String json = gson.toJson(data);
//
//        ResponseEntity<List> response = restTemplate.getForEntity("/users", List.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
//        assertThat(gson.toJson(response.getBody())).isEqualTo(json);
//    }
}
