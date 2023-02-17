package com.example.springhomework;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public List<User> getAll() {
        return Collections.emptyList();
    }
}
