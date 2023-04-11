package com.example.springhomework.dto;

import com.example.springhomework.model.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

  private List<User> content;
  private Long totalElements;
  private int totalPages;
}
