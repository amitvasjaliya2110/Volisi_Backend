package com.volisi.handler;

import com.volisi.dto.request.QuizPaginationRequest;
import com.volisi.dto.request.QuizRequestByStatus;
import com.volisi.dto.request.QuizRequestRecord;
import com.volisi.dto.request.QuizUpdateRequest;
import com.volisi.dto.response.QuizLibraryResponseRecord;
import com.volisi.dto.response.QuizPageResponse;
import com.volisi.dto.response.QuizResponseRecord;
import com.volisi.entity.Quiz;
import com.volisi.mapper.QuizMapper;
import com.volisi.service.QuizService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Handles operations related to quizzes, serving as an intermediary between the controller and
 * service layers.
 */
@Component
public class QuizHandler {

  /**
   * Constructs a new QuizHandler with the specified QuizMapper and QuizService dependencies.
   *
   * @param quizMapper The QuizMapper responsible for mapping DTOs to entities and vice versa.
   * @param quizService The QuizService responsible for performing CRUD operations on quizzes.
   */
  private final QuizMapper quizMapper;

  private final QuizService quizService;

  public QuizHandler(QuizMapper quizMapper, QuizService quizService) {
    this.quizMapper = quizMapper;
    this.quizService = quizService;
  }

  /**
   * Adds a new quiz based on the provided quiz request DTO.
   *
   * @param quizRequest The request DTO containing details of the quiz to be added.
   * @return A response DTO containing the details of the added quiz.
   */
  public QuizResponseRecord addQuiz(QuizRequestRecord quizRequest) {
    Quiz quiz = quizMapper.toEntity(quizRequest);
    Quiz quizResponse = quizService.save(quiz);
    return quizMapper.toResponse(quizResponse);
  }

  public QuizResponseRecord getQuizById(Long id) {
    return quizMapper.toResponse(quizService.getQuizById(id));
  }

  public List<QuizResponseRecord> getAllQuiz() {
    return quizMapper.toList(quizService.getAll());
  }

  public QuizResponseRecord update(QuizUpdateRequest quizUpdateRequest) {
    Quiz quiz = quizMapper.toEntity(quizUpdateRequest);
    return quizMapper.toResponse(quizService.update(quiz));
  }

  public void delete(Long id) {
    quizService.delete(id);
  }

  public QuizPageResponse getQuizzesByCollectionAndUser(QuizPaginationRequest request) {
    Page<Quiz> quizPage = quizService.getQuizzesByCollectionAndUser(request);
    List<QuizLibraryResponseRecord> quizzes = quizMapper.toLibraryList(quizPage.getContent());
    return new QuizPageResponse(
        request.pageNo(), request.pageSize(), quizPage.getTotalElements(), quizzes);
  }

  public QuizPageResponse getQuizzesByUserAndStatus(QuizRequestByStatus quizRequestbyStatus) {
    Page<Quiz> quizPage = quizService.getQuizzesByUserAndStatus(quizRequestbyStatus);
    List<QuizLibraryResponseRecord> quizzes = quizMapper.toLibraryList(quizPage.getContent());
    return new QuizPageResponse(
        quizRequestbyStatus.pageNo(),
        quizRequestbyStatus.pageSize(),
        quizPage.getTotalElements(),
        quizzes);
  }
}
