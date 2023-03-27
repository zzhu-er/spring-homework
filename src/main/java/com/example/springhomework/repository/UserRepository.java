package com.example.springhomework.repository;

import com.example.springhomework.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
//    @Query("select u from User u where u.age= :age")
//    List<User> findAllByAge(@Param("age") Long age);
    List<User> findByAge(Long age);

    Page<User> findByAge(Pageable pageRequest, Long age);

    List<User> findByName(String name);

    Page<User> findByName(Pageable pageRequest, String name);

    @Query(value = "SELECT * FROM users WHERE created_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<User> findByCreatedAtBetween(Instant startDate, Instant endDate);

    @Query(value = "SELECT * FROM users WHERE created_at BETWEEN :startDate AND :endDate", nativeQuery = true)
    Page<User> findByCreatedAtBetween(Pageable pageRequest, Instant startDate, Instant endDate);
}
