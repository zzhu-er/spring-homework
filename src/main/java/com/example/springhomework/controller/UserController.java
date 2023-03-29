package com.example.springhomework.controller;

import com.example.springhomework.dto.Email;
import com.example.springhomework.dto.UserRequest;
import com.example.springhomework.model.User;
import com.example.springhomework.service.UserService;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private UserService userService;

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

}
