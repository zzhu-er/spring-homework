package com.example.springhomework;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Long age;
    private Instant createdAt;
    private Instant updatedAt;
}
