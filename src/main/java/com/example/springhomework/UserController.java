package com.example.springhomework;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private List<User> data = Collections.emptyList();
    private UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }

    public void save(List<User> data) {
        this.data = data;
    }

    public void deleteAll() {
        this.data = Collections.emptyList();
    }
}
