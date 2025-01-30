package com.volisi.handler;

import com.volisi.dto.request.OtpRequest;
import com.volisi.dto.request.UserPatchRequest;
import com.volisi.dto.request.UserRequest;
import com.volisi.dto.request.UserUpdateRequest;
import com.volisi.dto.response.OtpResponse;
import com.volisi.dto.response.UserIdResponse;
import com.volisi.dto.response.UserResponse;
import com.volisi.entity.User;
import com.volisi.mapper.UserMapper;
import com.volisi.service.AuthenticationService;
import com.volisi.service.UserService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserHandler {
  private final UserMapper userMapper;
  private final UserService userService;
  private final AuthenticationService authenticationService;

  public UserHandler(
      UserService userService, UserMapper userMapper, AuthenticationService authenticationService) {
    this.userService = userService;
    this.userMapper = userMapper;
    this.authenticationService = authenticationService;
  }

  public UserResponse add(UserRequest userRequest) {
    User user = userMapper.toEntity(userRequest);
    return userMapper.toResponse(userService.save(user));
  }

  public UserResponse getById(Long id) {
    return userMapper.toResponse(userService.getById(id));
  }

  public List<UserResponse> getAll() {
    return userMapper.toList(userService.getAll());
  }

  public UserResponse update(UserUpdateRequest userUpdateRequest) {
    User user = userMapper.toUpdateEntity(userUpdateRequest);
    return userMapper.toResponse(userService.update(user));
  }

  public UserResponse patchUpdate(Long id, UserPatchRequest userPatchRequest) {
    User user = userMapper.toPatchEntity(id, userPatchRequest);
    return userMapper.toResponse(userService.patchUpdate(user));
  }

  public void delete(Long id) {
    userService.delete(id);
  }

  public void verifyOtp(OtpRequest otpRequest) {
    authenticationService.verifyOtp(userMapper.toEntity(otpRequest));
  }

  public UserIdResponse findByUsername(String username) {
    return userMapper.toResponseId(userService.findByUsername(username));
  }

  public OtpResponse generateOtp(Long id) {
    return userMapper.toOtpResponse(authenticationService.generateOtp(id));
  }
}
