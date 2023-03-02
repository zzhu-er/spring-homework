package com.example.springhomework;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
//    @Query("select u from User u where u.age= :age")
//    List<User> findAllByAge(@Param("age") Long age);
    List<User> findByAge(Long age);

    Page<User> findByAge(Pageable pageRequest, Long age);

    List<User> findByName(String name);

    Page<User> findByName(Pageable pageRequest, String name);
}
