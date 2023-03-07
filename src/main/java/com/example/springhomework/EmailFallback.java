package com.example.springhomework;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailFallback implements EmailClient{

    @Override
    public List<Email> getById(Long id) throws Exception {
        System.out.println("----------------------------------");
        throw new Exception("Email Service Seems Not Work");
    }

    @Override
    public ResponseEntity<String> saveEmail(Long userId, List<Email> savedEmails) throws Exception {
        System.out.println("----------------------------------");
        throw new Exception("Email Service Seems Not Work");
    }
}
