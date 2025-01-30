package com.volisi.model;

import lombok.Data;

@Data
public class QuizDto {
  private Long id;
  private String name;
  private String description;
  private String coverImage;
  private String status;
  private CollectionDto collection;
  private UserDto user;
  private boolean deleted;
}
