package com.example.springhomework.service;

import com.example.springhomework.dto.Email;
import com.example.springhomework.dto.PageResponse;
import com.example.springhomework.dto.UserRequest;
import com.example.springhomework.dto.UserResponse;
import com.example.springhomework.model.User;
import com.example.springhomework.repository.UserRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
  public void save(UserRequest userRequest) throws Exception {
    User user = User.builder().name(userRequest.getName()).age(userRequest.getAge()).build();
    List<Email> savedEmails = userRequest.getEmails();
    User savedUser = userRepository.save(user);
    emailClient.saveEmail(savedUser.getId(), savedEmails);
  }

  public void delete(User deletedUser) {
    userRepository.delete(deletedUser);
  }

  public void update(User updatedUser) {
    userRepository.save(updatedUser);
  }

  public List<Email> getEmailsByUserId(Long id) throws Exception {
    return emailClient.getById(id);
  }

  public PageResponse<UserResponse> findAllDynamicallyWithPagination(Integer page, Integer size,
      Long age, String name, Instant startDate, Instant endDate) {
    Specification<User> specification = getUserSpecification(age, name, startDate, endDate);
    PageResponse<UserResponse> pageResponse = new PageResponse<>();
    UserResponse userResponse = new UserResponse();
    pageResponse.setContent(userResponse);
    Pageable pagination = PageRequest.of(page, size);
    Page<User> userPages = userRepository.findAll(specification, pagination);
    userResponse.setContent(userPages.getContent());
    pageResponse.setTotalPages(userPages.getTotalPages());
    pageResponse.setTotalElements(userPages.getTotalElements());
    pageResponse.setCurrentPage(userPages.getNumber());
    return pageResponse;
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public void deleteEmailByEmailId(Long emailId) throws Exception {
    emailClient.deleteEmail(emailId);
  }

  public void saveEmailUnderUser(Long id, List<Email> emailList) throws Exception {
    emailClient.saveEmail(id, emailList);
  }

  private static Specification<User> getUserSpecification(Long age, String name, Instant startDate,
      Instant endDate) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (age != null) {
        Predicate agePredicate = criteriaBuilder.equal(root.get("age"), age);
        predicateList.add(agePredicate);
      }
      if (name != null) {
        Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
        predicateList.add(namePredicate);
      }
      if (startDate != null) {
        Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"),
            startDate);
        predicateList.add(startDatePredicate);
      }
      if (endDate != null) {
        Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"),
            endDate);
        predicateList.add(endDatePredicate);
      }
      Predicate[] predicates = new Predicate[predicateList.size()];
      return criteriaBuilder.and(predicateList.toArray(predicates));
    };
  }

}
