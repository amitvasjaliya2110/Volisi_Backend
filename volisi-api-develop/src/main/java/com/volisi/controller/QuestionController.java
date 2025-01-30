package com.volisi.controller;

import com.volisi.constant.Constant;
import com.volisi.dto.BaseResponse;
import com.volisi.dto.PageBaseResponse;
import com.volisi.dto.request.QuestionRequest;
import com.volisi.dto.request.QuestionUpdateRequest;
import com.volisi.dto.response.QuestionResponse;
import com.volisi.enums.ResultCode;
import com.volisi.handler.QuestionHandler;
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
@RequestMapping("/api/questions")
public class QuestionController {
  private final QuestionHandler questionHandler;
  private final MessageSource messageSource;

  public QuestionController(QuestionHandler questionHandler, MessageSource messageSource) {
    this.questionHandler = questionHandler;
    this.messageSource = messageSource;
  }

  @PostMapping
  public BaseResponse<QuestionResponse> add(@Valid @RequestBody QuestionRequest questionRequest) {
    QuestionResponse response = questionHandler.add(questionRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "saved.data.success", new Object[] {Constant.QUESTION}, Locale.getDefault()),
        response);
  }

  @GetMapping
  public BaseResponse<List<QuestionResponse>> getAll() {
    List<QuestionResponse> response = questionHandler.getAll();
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.QUESTIONS}, Locale.getDefault()),
        response);
  }

  @GetMapping("/{id}")
  public BaseResponse<QuestionResponse> getById(@PathVariable Long id) {
    QuestionResponse response = questionHandler.getById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.QUESTION}, Locale.getDefault()),
        response);
  }

  @PutMapping()
  public BaseResponse<QuestionResponse> update(
      @Valid @RequestBody QuestionUpdateRequest questionUpdateRequest) {
    QuestionResponse response = questionHandler.update(questionUpdateRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "updated.data.success", new String[] {Constant.QUESTION}, Locale.getDefault()),
        response);
  }

  @DeleteMapping("/{id}")
  public BaseResponse<String> delete(@PathVariable Long id) {
    questionHandler.delete(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "delete.data.success", new Object[] {Constant.QUESTION}, Locale.getDefault()));
  }

  @GetMapping("/quiz/{id}")
  public PageBaseResponse<QuestionResponse> getQuestionsByQuizId(
      @PathVariable("id") Long id,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "pageSize", defaultValue = "50") int pageSize) {

    Page<QuestionResponse> responsePage = questionHandler.getQuestionsByQuizId(id, page, pageSize);

    return new PageBaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.QUESTIONS}, Locale.getDefault()),
        page,
        pageSize,
        responsePage.getTotalElements(),
        responsePage.getContent());
  }
}
