package com.example.springhomework;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@Component
public class EmailFallback implements EmailClient{

    @Override
    public List<Email> getById(Long id){
        return Collections.emptyList();
    }

    @Override
    public ResponseEntity<String> saveEmail(Long userId, List<Email> savedEmails) {
        throw new RuntimeException("Email Service Not Working");
    }
}
