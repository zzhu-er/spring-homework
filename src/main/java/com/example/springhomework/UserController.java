package com.example.springhomework;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private List<User> data = Collections.emptyList();

    @GetMapping
    public List<User> getAll() {
        return data;
    }

    public void save(List<User> data) {
        this.data = data;
    }

    public void deleteAll() {
        this.data = Collections.emptyList();
    }
}
