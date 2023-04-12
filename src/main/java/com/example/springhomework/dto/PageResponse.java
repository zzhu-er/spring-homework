package com.example.springhomework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

  private T content;
  private int totalPages;
  private Long totalElements;
  private int currentPage;
}
