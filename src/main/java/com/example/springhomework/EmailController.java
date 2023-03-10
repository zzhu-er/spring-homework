package com.example.springhomework;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

