package com.example.springhomework;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<String> save(@RequestBody User savedUser) {
        userService.save(savedUser);
        return new ResponseEntity<>("USER SAVED SUCCESSFULLY", HttpStatus.CREATED);
    }

    public void deleteAll() {
        this.data = Collections.emptyList();
    }
}
