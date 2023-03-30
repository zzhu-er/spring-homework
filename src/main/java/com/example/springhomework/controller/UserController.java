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
  public List<User> queryAllDynamically(@RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size,
      @RequestParam(required = false) Long age,
      @RequestParam(required = false) String name) {
    if (page == null || size == null) {
      return userService.findAllDynamically(age, name);
    }
    return userService.findAllDynamicallyWithPagination(page, size, age, name);
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
