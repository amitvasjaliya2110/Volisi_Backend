package com.volisi.model;

import lombok.Data;

@Data
public class UserDto {

  private Long id;
  private String username;
  private String email;
  private boolean active;
}
