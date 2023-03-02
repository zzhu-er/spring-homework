package com.example.springhomework;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public List<User> findAll(int page, int size) {
        Pageable firstPageWithThreeItems = PageRequest.of(page, size);
        Page<User> allUsers = userRepository.findAll(firstPageWithThreeItems);
        return allUsers.getContent();
    }

    public void save(User savedUser) {
        userRepository.save(savedUser);
    }

    public void delete(User deletedUser) {
        userRepository.delete(deletedUser);
    }

    public void update(User updatedUser) throws Exception {
        User savedUser = userRepository.findById(updatedUser.getId()).orElse(null);
        if (savedUser != null) {
            savedUser.setName(updatedUser.getName());
            savedUser.setAge(updatedUser.getAge());
            userRepository.save(updatedUser);
        } else {
            throw new Exception("USER NOT FOUND");
        }
    }
}
