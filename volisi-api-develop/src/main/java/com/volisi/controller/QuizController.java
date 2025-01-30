package com.volisi.controller;

import com.volisi.constant.Constant;
import com.volisi.dto.BaseResponse;
import com.volisi.dto.request.QuizPaginationRequest;
import com.volisi.dto.request.QuizRequestByStatus;
import com.volisi.dto.request.QuizRequestRecord;
import com.volisi.dto.request.QuizUpdateRequest;
import com.volisi.dto.response.QuizPageResponse;
import com.volisi.dto.response.QuizResponseRecord;
import com.volisi.enums.ResultCode;
import com.volisi.handler.QuizHandler;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller class for handling operations related to quizzes. */
@RestController
@RequestMapping("api/quiz")
public class QuizController {

  /**
   * Constructor to initialize the QuizController with a QuizHandler dependency.
   *
   * @param quizHandler The QuizHandler responsible for handling quiz-related operations.
   */
  private final QuizHandler quizHandler;

  private final MessageSource messageSource;

  public QuizController(QuizHandler quizHandler, MessageSource messageSource) {
    this.quizHandler = quizHandler;
    this.messageSource = messageSource;
  }

  /**
   * Endpoint to add a new quiz.
   *
   * @param quizRequest The request DTO containing details of the quiz to be added.
   * @return A response DTO indicating the result of the operation.
   */
  @PostMapping
  public BaseResponse<QuizResponseRecord> add(@Valid @RequestBody QuizRequestRecord quizRequest) {

    QuizResponseRecord response = quizHandler.addQuiz(quizRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "saved.data.success", new String[] {Constant.QUIZ}, LocaleContextHolder.getLocale()),
        response);
  }

  @GetMapping("/{id}")
  public BaseResponse<QuizResponseRecord> getQuizById(@PathVariable("id") Long id) {
    QuizResponseRecord response = quizHandler.getQuizById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "quiz.data.success", new Object[] {}, LocaleContextHolder.getLocale()),
        response);
  }

  @GetMapping
  public BaseResponse<List<QuizResponseRecord>> getAll() {
    List<QuizResponseRecord> response = quizHandler.getAllQuiz();
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "quiz.data.success", new Object[] {}, LocaleContextHolder.getLocale()),
        response);
  }

  @PostMapping("/getQuizByCollectionAndUser")
  public BaseResponse<QuizPageResponse> getQuizzesByCollectionAndUser(
      @Valid @RequestBody QuizPaginationRequest request) {
    QuizPageResponse response = quizHandler.getQuizzesByCollectionAndUser(request);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "quiz.data.success", new Object[] {Constant.QUIZ}, LocaleContextHolder.getLocale()),
        response);
  }

  @PutMapping
  public BaseResponse<QuizResponseRecord> update(
      @Valid @RequestBody QuizUpdateRequest quizUpdateRequest) {
    QuizResponseRecord updatedQuiz = quizHandler.update(quizUpdateRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "quiz.update.success", new Object[] {}, LocaleContextHolder.getLocale()),
        updatedQuiz);
  }

  @DeleteMapping("/{id}")
  public BaseResponse<String> delete(@PathVariable Long id) {
    quizHandler.delete(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "delete.data.success", new Object[] {Constant.QUIZ}, LocaleContextHolder.getLocale()));
  }

  @PostMapping("/user")
  public BaseResponse<QuizPageResponse> getQuizzesByUserAndStatus(
      @Valid @RequestBody QuizRequestByStatus quizRequestbyStatus) {
    QuizPageResponse response = quizHandler.getQuizzesByUserAndStatus(quizRequestbyStatus);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "quiz.data.success", new Object[] {}, LocaleContextHolder.getLocale()),
        response);
  }
}
