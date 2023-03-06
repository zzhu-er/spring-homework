package com.example.springhomework;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
@AllArgsConstructor
public class EmailController {

    private EmailClient emailClient;

    @GetMapping("/{id}")
    public Email getById(@PathVariable Long id) {
        return emailClient.getById(id);
    }
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Email savedEmail) {
        return emailClient.saveEmail(savedEmail);
    }
}

