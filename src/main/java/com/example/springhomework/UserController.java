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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        User deletedUser = User.builder().id(id).build();
        userService.delete(deletedUser);
        return new ResponseEntity<>("USER DELETED SUCCESSFULLY", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody User updatedUser) throws Exception {
        updatedUser.setId(id);
        userService.update(updatedUser);
        return new ResponseEntity<>("USER UPDATED SUCCESSFULLY", HttpStatus.OK);
    }

    public void deleteAll() {
        this.data = Collections.emptyList();
    }
}
