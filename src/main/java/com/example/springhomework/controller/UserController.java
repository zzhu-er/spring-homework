package com.example.springhomework.controller;

import com.example.springhomework.dto.Email;
import com.example.springhomework.dto.UserRequest;
import com.example.springhomework.model.User;
import com.example.springhomework.service.UserService;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private UserService userService;

  @GetMapping
  public List<User> queryAllDynamically(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size,
      @RequestParam(required = false) Long age,
      @RequestParam(required = false) String name,
      @RequestParam(value = "from", required = false)
      @DateTimeFormat(iso = ISO.DATE_TIME) Instant startDate,
      @RequestParam(value = "to", required = false)
      @DateTimeFormat(iso = ISO.DATE_TIME) Instant endDate) {
    if (page == null || size == null) {
      return userService.findAllDynamically(age, name, startDate, endDate);
    }
    return userService.findAllDynamicallyWithPagination(page, size, age, name, startDate, endDate);
  }

  @PostMapping
  public ResponseEntity<String> save(@RequestBody UserRequest userRequest) throws Exception {
    return userService.save(userRequest);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    User deletedUser = User.builder().id(id).build();
    userService.delete(deletedUser);
    return new ResponseEntity<>("USER DELETED SUCCESSFULLY", HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> update(@PathVariable Long id, @RequestBody User updatedUser) {
    updatedUser.setId(id);
    userService.update(updatedUser);
    return new ResponseEntity<>("USER UPDATED SUCCESSFULLY", HttpStatus.OK);
  }

  @GetMapping("/{id}/emails")
  public List<Email> getEmailsByUserId(@PathVariable Long id) throws Exception {
    return userService.getEmailsByUserId(id);
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    Optional<User> userById = userService.getUserById(id);
    if (userById.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          String.format("User with id %d not existed", id));
    }
    return userById.get();
  }

  @DeleteMapping("/{id}/emails/{emailId}")
  public ResponseEntity<String> deleteEmailFromUser(@PathVariable Long id,
      @PathVariable Long emailId) throws Exception {
    userService.deleteEmailByEmailId(emailId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/{id}/emails")
  public ResponseEntity<String> saveEmailsUnderUser(@PathVariable Long id, @RequestBody List<Email> emailList)
      throws Exception {
    userService.saveEmailUnderUser(id, emailList);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
