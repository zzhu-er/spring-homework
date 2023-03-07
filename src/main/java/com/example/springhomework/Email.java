package com.example.springhomework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private Long id;
    private Long userId = 0L;
    private String email;
}
