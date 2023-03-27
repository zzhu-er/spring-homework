package com.example.springhomework.controller;

import com.example.springhomework.dto.Email;
import com.example.springhomework.service.EmailClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emails")
@AllArgsConstructor
public class EmailController {

    private EmailClient emailClient;

    @GetMapping("/{id}")
    public List<Email> getByUserId(@PathVariable Long id) throws Exception {
        return emailClient.getById(id);
    }
}

