package com.example.springhomework;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void save(User savedUser) {
        userRepository.save(savedUser);
    }

    public void delete(User deletedUser) {
        userRepository.delete(deletedUser);
    }

    public void update(User refEq) {

    }
}
