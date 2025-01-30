package com.volisi.controller;

import com.volisi.constant.Constant;
import com.volisi.dto.BaseResponse;
import com.volisi.dto.request.UserPatchRequest;
import com.volisi.dto.request.UserRequest;
import com.volisi.dto.request.UserUpdateRequest;
import com.volisi.dto.response.UserIdResponse;
import com.volisi.dto.response.UserResponse;
import com.volisi.enums.ResultCode;
import com.volisi.handler.UserHandler;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
  private final UserHandler userHandler;

  private final MessageSource messageSource;

  public UserController(UserHandler userHandler, MessageSource messageSource) {
    this.userHandler = userHandler;
    this.messageSource = messageSource;
  }

  @PostMapping
  public BaseResponse<UserResponse> add(@Valid @RequestBody UserRequest userRequest) {
    UserResponse response = userHandler.add(userRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "saved.data.success", new String[] {Constant.USER}, Locale.getDefault()),
        response);
  }

  @GetMapping("/{id}")
  public BaseResponse<UserResponse> getById(@PathVariable Long id) {
    UserResponse response = userHandler.getById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new String[] {Constant.USER}, Locale.getDefault()),
        response);
  }

  @GetMapping
  public BaseResponse<List<UserResponse>> getAll() {
    List<UserResponse> response = userHandler.getAll();
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new String[] {Constant.USER}, Locale.getDefault()),
        response);
  }

  @PutMapping
  public BaseResponse<UserResponse> update(
      @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
    UserResponse response = userHandler.update(userUpdateRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "updated.data.success", new String[] {Constant.USER}, Locale.getDefault()),
        response);
  }

  @PatchMapping("/{id}")
  public BaseResponse<UserResponse> patchUpdate(
      @PathVariable Long id, @Valid @RequestBody UserPatchRequest userPatchRequest) {
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "updated.data.success", new String[] {Constant.USER}, Locale.getDefault()),
        userHandler.patchUpdate(id, userPatchRequest));
  }

  @DeleteMapping("/{id}")
  public BaseResponse<UserResponse> delete(@PathVariable Long id) {
    userHandler.delete(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "delete.data.success", new String[] {Constant.USER}, Locale.getDefault()),
        null);
  }

  @GetMapping("/username/{username}")
  public BaseResponse<UserIdResponse> getByUsername(@PathVariable String username) {
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new String[] {Constant.USER}, Locale.getDefault()),
        userHandler.findByUsername(username));
  }
}
