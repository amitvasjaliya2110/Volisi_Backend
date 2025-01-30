package com.volisi.controller;

import com.volisi.constant.Constant;
import com.volisi.dto.BaseResponse;
import com.volisi.dto.PageBaseResponse;
import com.volisi.dto.request.PlayerRequest;
import com.volisi.dto.request.PlayerUpdateRequest;
import com.volisi.dto.response.PlayerResponse;
import com.volisi.enums.ResultCode;
import com.volisi.handler.PlayerHandler;
import com.volisi.projection.PlayerQuizQuestion;
import jakarta.validation.Valid;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

  private final PlayerHandler playerHandler;
  private final MessageSource messageSource;

  public PlayerController(PlayerHandler playerHandler, MessageSource messageSource) {
    this.playerHandler = playerHandler;
    this.messageSource = messageSource;
  }

  @PostMapping
  public BaseResponse<PlayerResponse> create(@RequestBody @Valid PlayerRequest playerRequest) {
    PlayerResponse response = playerHandler.create(playerRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "saved.data.success", new Object[] {Constant.PLAYER}, Locale.getDefault()),
        response);
  }

  @GetMapping("/playerQuiz/{Id}")
  public PageBaseResponse<PlayerQuizQuestion> getPlayerByPlayerQuiz(
      @PathVariable("Id") Long Id,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
    Page<PlayerQuizQuestion> responsePage = playerHandler.getPlayerByPlayerQuiz(Id, page, pageSize);
    return new PageBaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.PLAYER}, Locale.getDefault()),
        page,
        pageSize,
        responsePage.getTotalElements(),
        responsePage.getContent());
  }

  @GetMapping("/{id}")
  public BaseResponse<PlayerResponse> getById(@PathVariable Long id) {
    PlayerResponse response = playerHandler.getById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.PLAYER}, Locale.getDefault()),
        response);
  }

  @PutMapping
  public BaseResponse<PlayerResponse> update(
      @RequestBody @Valid PlayerUpdateRequest playerUpdateRequest) {
    PlayerResponse response = playerHandler.update(playerUpdateRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "updated.data.success", new Object[] {Constant.PLAYER}, Locale.getDefault()),
        response);
  }

  @DeleteMapping("/{id}")
  public BaseResponse<String> delete(@PathVariable Long id) {
    playerHandler.delete(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "delete.data.success", new Object[] {Constant.PLAYER}, Locale.getDefault()));
  }
}
