package com.example.springhomework.service;

import com.example.springhomework.model.User;
import com.example.springhomework.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;

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

  public void save(User savedUser) {
    userRepository.save(savedUser);
  }

  public void delete(User deletedUser) {
    userRepository.delete(deletedUser);
  }

  public void update(User updatedUser) {
    User savedUser = userRepository.findById(updatedUser.getId()).orElse(null);

    savedUser.setName(updatedUser.getName());
    savedUser.setAge(updatedUser.getAge());
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
