package com.example.springhomework;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserRequest {
    private String name;
    private Long age;
    private List<Email> emails;
}
