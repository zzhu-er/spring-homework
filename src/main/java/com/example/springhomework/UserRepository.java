package com.example.springhomework;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from User u where u.age= :age")
    List<User> findAllByAge(@Param("age") Long age);
}
