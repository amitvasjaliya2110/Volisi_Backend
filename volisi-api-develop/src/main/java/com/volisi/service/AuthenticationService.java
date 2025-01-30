package com.volisi.service;

import com.volisi.dto.response.LoginResponse;
import com.volisi.entity.User;

public interface AuthenticationService {
  LoginResponse authenticate(User entity);

  String refreshToken(String token);

  void logout(String token);

  void verifyOtp(User user);

  User generateOtp(Long id);
}
