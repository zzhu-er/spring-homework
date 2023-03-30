package com.example.springhomework.service;

import com.example.springhomework.dto.Email;
import com.example.springhomework.dto.UserRequest;
import com.example.springhomework.model.User;
import com.example.springhomework.repository.UserRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;
  private EmailClient emailClient;

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

  public List<User> findAllBetweenDates(Instant startDate, Instant endDate) {
    return userRepository.findByCreatedAtBetween(startDate, endDate);
  }

  public List<User> findAllBetweenDates(Integer page, Integer size, Instant startDate,
      Instant endDate) {
    Pageable pagination = PageRequest.of(page, size);
    Page<User> allUsers = userRepository.findByCreatedAtBetween(pagination, startDate, endDate);
    return allUsers.getContent();
  }

  public List<Email> getEmailsByUserId(Long id) throws Exception {
    return emailClient.getById(id);
  }

  public List<User> findAllDynamically(Long age, String name) {
    Specification<User> specification = getUserSpecification(age, name);
    return userRepository.findAll(specification);
  }

//  given: when userRepository.findAll(argThat(arg -> { arg.length == 3, arg.[0] .key =="name"}))
//  .thenReturn(expected)

//  when
//  val result = subject.findAllDynamically(1, 2)

  public List<User> findAllDynamicallyWithPagination(Integer page, Integer size, Long age,
      String name) {
    Specification<User> specification = getUserSpecification(age, name);
    Pageable pagination = PageRequest.of(page, size);
    Page<User> allUsers = userRepository.findAll(specification, pagination);
    return allUsers.getContent();
  }

  private static Specification<User> getUserSpecification(Long age, String name) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (age != null) {
        Predicate agePredicate = criteriaBuilder.equal(root.get("age"), age);
        predicateList.add(agePredicate);
      }
      if (name != null) {
        Predicate namePredicate = criteriaBuilder.equal(root.get("name"), name);
        predicateList.add(namePredicate);
      }
      Predicate[] predicates = new Predicate[predicateList.size()];
      return criteriaBuilder.and(predicateList.toArray(predicates));
    };
  }
}
