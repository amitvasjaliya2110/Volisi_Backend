package com.volisi.controller;

import com.volisi.constant.Constant;
import com.volisi.dto.BaseResponse;
import com.volisi.dto.PageBaseResponse;
import com.volisi.dto.request.PlayerQuizQuestionRequest;
import com.volisi.dto.request.PlayerQuizQuestionUpdateRequest;
import com.volisi.dto.response.PlayerQuizQuestionAnswerResponse;
import com.volisi.dto.response.PlayerQuizQuestionResponse;
import com.volisi.enums.ResultCode;
import com.volisi.handler.PlayerQuizQuestionHandler;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/api/player/quiz-question")
public class PlayerQuizQuestionController {
  PlayerQuizQuestionHandler playerQuizQuestionHandler;

  MessageSource messageSource;

  public PlayerQuizQuestionController(
      PlayerQuizQuestionHandler playerQuizQuestionHandler, MessageSource messageSource) {
    this.playerQuizQuestionHandler = playerQuizQuestionHandler;
    this.messageSource = messageSource;
  }

  @PostMapping
  public BaseResponse<PlayerQuizQuestionResponse> add(
      @Valid @RequestBody PlayerQuizQuestionRequest playerQuizQuestionRequest) {
    playerQuizQuestionHandler.add(playerQuizQuestionRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "saved.data.success",
            new Object[] {Constant.PLAYER_QUIZ_QUESTION},
            Locale.getDefault()));
  }

  @GetMapping
  public BaseResponse<List<PlayerQuizQuestionResponse>> getAll() {
    List<PlayerQuizQuestionResponse> response = playerQuizQuestionHandler.getAll();
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success",
            new Object[] {Constant.PLAYER_QUIZ_QUESTION},
            Locale.getDefault()),
        response);
  }

  @GetMapping("/{id}")
  public BaseResponse<PlayerQuizQuestionResponse> getById(@PathVariable Long id) {
    PlayerQuizQuestionResponse response = playerQuizQuestionHandler.getById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success",
            new Object[] {Constant.PLAYER_QUIZ_QUESTION},
            Locale.getDefault()),
        response);
  }

  @GetMapping("/playerQuiz/{id}")
  public PageBaseResponse<PlayerQuizQuestionAnswerResponse> getAllByPlayerQuizId(
      @PathVariable("id") Long id,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {

    Page<PlayerQuizQuestionAnswerResponse> responsePage =
        playerQuizQuestionHandler.getAllByPlayerQuizId(id, page, pageSize);

    return new PageBaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success",
            new Object[] {Constant.PLAYER_QUIZ_QUESTION},
            Locale.getDefault()),
        page,
        pageSize,
        responsePage.getTotalElements(),
        responsePage.getContent());
  }

  @PutMapping
  public BaseResponse<PlayerQuizQuestionResponse> update(
      @Valid @RequestBody PlayerQuizQuestionUpdateRequest updateRequest) {
    PlayerQuizQuestionResponse response = playerQuizQuestionHandler.update(updateRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "updated.data.success",
            new Object[] {Constant.PLAYER_QUIZ_QUESTION},
            Locale.getDefault()),
        response);
  }

  @DeleteMapping("/{id}")
  public BaseResponse<String> deleteById(@PathVariable Long id) {
    playerQuizQuestionHandler.deleteById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "delete.data.success",
            new Object[] {Constant.PLAYER_QUIZ_QUESTION},
            Locale.getDefault()));
  }

  @GetMapping("/player/{id}")
  public PageBaseResponse<PlayerQuizQuestionResponse> getByPlayerId(
      @PathVariable("id") Long id,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {

    Page<PlayerQuizQuestionResponse> responsePage =
        playerQuizQuestionHandler.getByPlayerId(id, page, pageSize);

    return new PageBaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success",
            new Object[] {Constant.PLAYER_QUIZ_QUESTION},
            Locale.getDefault()),
        page,
        pageSize,
        responsePage.getTotalElements(),
        responsePage.getContent());
  }
}
