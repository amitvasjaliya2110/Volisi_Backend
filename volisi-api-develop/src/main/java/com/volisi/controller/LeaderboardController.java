package com.volisi.controller;

import com.volisi.constant.Constant;
import com.volisi.dto.BaseResponse;
import com.volisi.dto.request.LeaderboardRequest;
import com.volisi.dto.request.LeaderboardUpdateRequest;
import com.volisi.dto.response.LeaderboardResponse;
import com.volisi.enums.ResultCode;
import com.volisi.handler.LeaderboardHandler;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
  private final LeaderboardHandler leaderboardHandler;
  private final MessageSource messageSource;

  public LeaderboardController(LeaderboardHandler leaderboardHandler, MessageSource messageSource) {
    this.leaderboardHandler = leaderboardHandler;
    this.messageSource = messageSource;
  }

  @PostMapping
  public BaseResponse<LeaderboardResponse> create(
      @Valid @RequestBody LeaderboardRequest leaderboardRequest) {
    LeaderboardResponse response = leaderboardHandler.create(leaderboardRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "saved.data.success", new Object[] {Constant.LEADERBOARD}, Locale.getDefault()),
        response);
  }

  @GetMapping
  public BaseResponse<List<LeaderboardResponse>> getAll() {
    List<LeaderboardResponse> response = leaderboardHandler.getAll();
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.LEADERBOARD}, Locale.getDefault()),
        response);
  }

  @GetMapping("/{id}")
  public BaseResponse<LeaderboardResponse> getById(@PathVariable Long id) {
    LeaderboardResponse response = leaderboardHandler.getById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.LEADERBOARD}, Locale.getDefault()),
        response);
  }

  @PutMapping
  public BaseResponse<LeaderboardResponse> update(
      @RequestBody LeaderboardUpdateRequest leaderboardUpdateRequest) {
    LeaderboardResponse response = leaderboardHandler.update(leaderboardUpdateRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "updated.data.success", new Object[] {Constant.LEADERBOARD}, Locale.getDefault()),
        response);
  }

  @DeleteMapping("/{id}")
  public BaseResponse<LeaderboardResponse> delete(@PathVariable Long id) {
    leaderboardHandler.delete(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "delete.data.success", new Object[] {Constant.LEADERBOARD}, Locale.getDefault()));
  }
}
