package com.volisi.controller;

import com.volisi.constant.Constant;
import com.volisi.dto.BaseResponse;
import com.volisi.dto.request.AnswerOptionRequest;
import com.volisi.dto.request.AnswerOptionUpdateRequest;
import com.volisi.dto.response.AnswerOptionResponse;
import com.volisi.enums.ResultCode;
import com.volisi.handler.AnswerOptionHandler;
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
@RequestMapping("/api/answer-option")
public class AnswerOptionController {
  private final AnswerOptionHandler answerOptionHandler;
  private final MessageSource messageSource;

  public AnswerOptionController(
      AnswerOptionHandler answerOptionHandler, MessageSource messageSource) {
    this.answerOptionHandler = answerOptionHandler;
    this.messageSource = messageSource;
  }

  @PostMapping
  public BaseResponse<AnswerOptionResponse> create(
      @Valid @RequestBody AnswerOptionRequest answerOptionRequest) {
    AnswerOptionResponse response = answerOptionHandler.create(answerOptionRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "saved.data.success", new Object[] {Constant.ANSWER_OPTION}, Locale.getDefault()),
        response);
  }

  @GetMapping
  public BaseResponse<List<AnswerOptionResponse>> getAll() {
    List<AnswerOptionResponse> response = answerOptionHandler.getAll();
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.ANSWER_OPTION}, Locale.getDefault()),
        response);
  }

  @GetMapping("/{id}")
  public BaseResponse<AnswerOptionResponse> getById(@PathVariable Long id) {
    AnswerOptionResponse response = answerOptionHandler.getById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.ANSWER_OPTION}, Locale.getDefault()),
        response);
  }

  @PutMapping
  public BaseResponse<AnswerOptionResponse> update(
      @Valid @RequestBody AnswerOptionUpdateRequest answerOptionUpdateRequest) {
    AnswerOptionResponse response = answerOptionHandler.update(answerOptionUpdateRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "updated.data.success", new Object[] {Constant.ANSWER_OPTION}, Locale.getDefault()),
        response);
  }

  @DeleteMapping("/{id}")
  public BaseResponse<AnswerOptionResponse> delete(@PathVariable Long id) {
    answerOptionHandler.delete(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "delete.data.success", new Object[] {Constant.ANSWER_OPTION}, Locale.getDefault()));
  }
}
