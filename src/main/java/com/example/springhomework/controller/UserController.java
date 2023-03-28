package com.example.springhomework.controller;

import com.example.springhomework.model.User;
import com.example.springhomework.dto.Email;
import com.example.springhomework.dto.UserRequest;
import com.example.springhomework.service.EmailClient;
import com.example.springhomework.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private UserService userService;
  private EmailClient emailClient;

  @GetMapping
  public List<User> getAll(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    if (page == null || size == null) {
      return userService.findAll();
    }
    return userService.findAll(page, size);
  }

  @GetMapping(value = "/", params = "age")
  public List<User> getAllByAge(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size, @RequestParam Long age) {
    if (page == null || size == null) {
      return userService.findByAge(age);
    }
    return userService.findByAge(page, size, age);
  }

  @GetMapping(value = "/", params = "name")
  public List<User> getAllByName(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size, @RequestParam String name) {
    if (page == null || size == null) {
      return userService.findByName(name);
    }
    return userService.findByName(page, size, name);
  }

  @GetMapping(value = "/", params = {"from", "to"})
  public List<User> getAllByTime(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size,
      @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
      @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate) {
    if (page == null || size == null) {
      return userService.findAllBetweenDates(startDate, endDate);
    }
    return userService.findAllBetweenDates(page, size, startDate, endDate);
  }

  @Transactional
  @PostMapping
  public ResponseEntity<String> save(@RequestBody UserRequest userRequest) throws Exception {
    User savedUser = User.builder().name(userRequest.getName()).age(userRequest.getAge()).build();
    List<Email> savedEmails = userRequest.getEmails();
    userService.save(savedUser);
    emailClient.saveEmail(savedUser.getId(), savedEmails);
    return new ResponseEntity<>("USER SAVED SUCCESSFULLY", HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    User deletedUser = User.builder().id(id).build();
    userService.delete(deletedUser);
    return new ResponseEntity<>("USER DELETED SUCCESSFULLY", HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> update(@PathVariable Long id, @RequestBody User updatedUser)
      throws Exception {
    updatedUser.setId(id);
    userService.update(updatedUser);
    return new ResponseEntity<>("USER UPDATED SUCCESSFULLY", HttpStatus.OK);
  }

  @GetMapping("/{id}/emails")
  public List<Email> getEmailsByUserId(@PathVariable Long id) throws Exception {
    return emailClient.getById(id);
  }
}
