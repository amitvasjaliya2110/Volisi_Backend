package com.volisi.handler;

import com.volisi.dto.request.LoginRequest;
import com.volisi.dto.response.LoginResponse;
import com.volisi.mapper.UserCredentialMapper;
import com.volisi.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AutheticationHandler {

  private final UserCredentialMapper userCredentialMapper;

  private final AuthenticationService authenticationService;

  public AutheticationHandler(
      UserCredentialMapper userCredentialMapper, AuthenticationService authenticationService) {
    this.userCredentialMapper = userCredentialMapper;
    this.authenticationService = authenticationService;
  }

  public LoginResponse login(LoginRequest loginRequest) {
    return authenticationService.authenticate(userCredentialMapper.toEntity(loginRequest));
  }

  public String refreshToken(String token) {
    return authenticationService.refreshToken(token);
  }

  public void logout(String token) {
    authenticationService.logout(token);
  }
}
