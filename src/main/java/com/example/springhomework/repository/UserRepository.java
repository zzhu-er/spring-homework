package com.example.springhomework.repository;

import com.example.springhomework.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

  //    @Query("select u from User u where u.age= :age")
//    List<User> findAllByAge(@Param("age") Long age);
  @Query(value = "SELECT * FROM users WHERE created_at BETWEEN :startDate AND :endDate", nativeQuery = true)
  List<User> findByCreatedAtBetween(Instant startDate, Instant endDate);

  @Query(value = "SELECT * FROM users WHERE created_at BETWEEN :startDate AND :endDate", nativeQuery = true)
  Page<User> findByCreatedAtBetween(Pageable pageRequest, Instant startDate, Instant endDate);
}
