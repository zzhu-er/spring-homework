package com.example.springhomework;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "emailservice", url = "http://localhost:8081/emails", configuration = FeignConfig.class)
public interface EmailClient {
    @GetMapping("/{id}")
    List<Email> getById(@PathVariable Long id);
    @PostMapping
    ResponseEntity<String> saveEmail(@RequestBody List<Email> savedEmails);
}
