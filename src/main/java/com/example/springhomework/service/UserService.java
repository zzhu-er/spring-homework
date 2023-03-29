package com.example.springhomework.service;

import com.example.springhomework.dto.Email;
import com.example.springhomework.dto.UserRequest;
import com.example.springhomework.model.User;
import com.example.springhomework.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;
  private EmailClient emailClient;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public List<User> findAll(int page, int size) {
    Pageable firstPageWithThreeItems = PageRequest.of(page, size);
    Page<User> allUsers = userRepository.findAll(firstPageWithThreeItems);
    return allUsers.getContent();
  }

  public List<User> findByAge(Long age) {
    return userRepository.findByAge(age);
  }

  public List<User> findByAge(Integer page, Integer size, Long age) {
    Pageable pagination = PageRequest.of(page, size);
    Page<User> allUsers = userRepository.findByAge(pagination, age);
    return allUsers.getContent();
  }

  @Transactional
  public ResponseEntity<String> save(UserRequest userRequest) throws Exception {
    User user = User.builder().name(userRequest.getName()).age(userRequest.getAge()).build();
    List<Email> savedEmails = userRequest.getEmails();
    User savedUser = userRepository.save(user);
    emailClient.saveEmail(savedUser.getId(), savedEmails);
    return new ResponseEntity<>("USER SAVED SUCCESSFULLY", HttpStatus.CREATED);
  }

  public void delete(User deletedUser) {
    userRepository.delete(deletedUser);
  }

  public void update(User updatedUser) {
//    User savedUser = userRepository.findById(updatedUser.getId()).orElse(null);
//
//    savedUser.setName(updatedUser.getName());
//    savedUser.setAge(updatedUser.getAge());
    userRepository.save(updatedUser);
  }

  public List<User> findByName(String name) {
    return userRepository.findByName(name);
  }

  public List<User> findByName(Integer page, Integer size, String name) {
    Pageable pagination = PageRequest.of(page, size);
    Page<User> allUsers = userRepository.findByName(pagination, name);
    return allUsers.getContent();
  }

  public List<User> findAllBetweenDates(Instant startDate, Instant endDate) {
    return userRepository.findByCreatedAtBetween(startDate, endDate);
  }

  public List<User> findAllBetweenDates(Integer page, Integer size, Instant startDate,
      Instant endDate) {
    Pageable pagination = PageRequest.of(page, size);
    Page<User> allUsers = userRepository.findByCreatedAtBetween(pagination, startDate, endDate);
    return allUsers.getContent();
  }
}
