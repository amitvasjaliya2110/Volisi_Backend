package com.volisi.controller;

import com.volisi.constant.Constant;
import com.volisi.dto.BaseResponse;
import com.volisi.dto.request.QuestionTypeRequest;
import com.volisi.dto.request.QuestionTypeUpdateRequest;
import com.volisi.dto.response.QuestionTypeResponse;
import com.volisi.enums.ResultCode;
import com.volisi.handler.QuestionTypeHandler;
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
@RequestMapping("/api/question/types")
public class QuestionTypeController {
  private final QuestionTypeHandler questionTypeHandler;
  private final MessageSource messageSource;

  public QuestionTypeController(
      QuestionTypeHandler questionTypeHandler, MessageSource messageSource) {
    this.questionTypeHandler = questionTypeHandler;
    this.messageSource = messageSource;
  }

  @PostMapping
  public BaseResponse<QuestionTypeResponse> create(
      @Valid @RequestBody QuestionTypeRequest questionTypeRequest) {
    QuestionTypeResponse response = questionTypeHandler.create(questionTypeRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "saved.data.success", new Object[] {Constant.QUESTION_TYPE}, Locale.getDefault()),
        response);
  }

  @GetMapping
  public BaseResponse<List<QuestionTypeResponse>> getAll() {
    List<QuestionTypeResponse> response = questionTypeHandler.getAll();
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.QUESTION_TYPE}, Locale.getDefault()),
        response);
  }

  @GetMapping("/{id}")
  public BaseResponse<QuestionTypeResponse> getById(@PathVariable Long id) {
    QuestionTypeResponse response = questionTypeHandler.getById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.QUESTION_TYPE}, Locale.getDefault()),
        response);
  }

  @PutMapping
  public BaseResponse<QuestionTypeResponse> update(
      @RequestBody @Valid QuestionTypeUpdateRequest questionTypeUpdateRequest) {
    QuestionTypeResponse response = questionTypeHandler.update(questionTypeUpdateRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "updated.data.success", new Object[] {Constant.QUESTION_TYPE}, Locale.getDefault()),
        response);
  }

  @DeleteMapping("/{id}")
  public BaseResponse<String> deleteById(@PathVariable Long id) {
    questionTypeHandler.deleteById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "delete.data.success", new Object[] {Constant.QUESTION_TYPE}, Locale.getDefault()));
  }
}
