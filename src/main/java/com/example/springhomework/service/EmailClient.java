package com.example.springhomework.service;

import com.example.springhomework.configuration.FeignConfig;
import com.example.springhomework.dto.Email;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "emailservice",
    url = "${service.endpoints.email}",
    configuration = FeignConfig.class,
    fallback = EmailFallback.class)
public interface EmailClient {

  @GetMapping("/{id}")
  List<Email> getById(@PathVariable Long id) throws Exception;

  @PostMapping("/{userId}")
  ResponseEntity<String> saveEmail(@PathVariable Long userId, @RequestBody List<Email> savedEmails)
      throws Exception;

  @DeleteMapping("/{id}")
  ResponseEntity<String> deleteEmail(@PathVariable Long id) throws Exception;
}
