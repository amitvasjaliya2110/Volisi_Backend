package com.volisi.handler;

import com.volisi.dto.request.PlayerPinRequest;
import com.volisi.dto.request.PlayerQuizRequestRecord;
import com.volisi.dto.request.PlayerQuizUpdateRequest;
import com.volisi.dto.response.PinVerifyResponse;
import com.volisi.dto.response.PlayerQuizResponseRecord;
import com.volisi.entity.PlayerQuiz;
import com.volisi.mapper.PlayerQuizMapper;
import com.volisi.projection.PlayerQuizQuestionTypeCount;
import com.volisi.service.PlayerQuizService;
import com.volisi.service.QuestionService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PlayerQuizHandler {
  private final PlayerQuizMapper playerQuizMapper;
  private final PlayerQuizService playerQuizService;
  private final QuestionService questionService;

  /**
   * Constructs a new PlayerQuizHandler with necessary dependencies.
   *
   * @param playerQuizMapper The mapper used for converting between DTOs and the entity.
   * @param playerQuizService The service responsible for the business logic of player quizzes.
   */
  public PlayerQuizHandler(
      PlayerQuizMapper playerQuizMapper,
      PlayerQuizService playerQuizService,
      QuestionService questionService) {
    this.playerQuizMapper = playerQuizMapper;
    this.playerQuizService = playerQuizService;
    this.questionService = questionService;
  }

  /**
   * Handles the creation of a PlayerQuiz based on the provided request.
   *
   * <p>This method maps the {@link PlayerQuizRequestRecord} to a {@link PlayerQuiz} entity, passes
   * it to the service layer for business processing, and then maps the entity back to a {@link
   * PlayerQuizResponseRecord} to be returned to the caller.
   *
   * @param request The {@link PlayerQuizRequestRecord} containing the details for the new quiz.
   * @return The {@link PlayerQuizResponseRecord} containing the details of the created quiz.
   */
  public PlayerQuizResponseRecord create(PlayerQuizRequestRecord request) {
    PlayerQuiz playerQuiz = playerQuizMapper.toEntity(request);
    return playerQuizMapper.toResponse(playerQuizService.create(playerQuiz));
  }

  public PlayerQuizResponseRecord getById(Long id) {
    PlayerQuiz playerQuiz = playerQuizService.getById(id);
    Long questionCountByQuiz = questionService.getQuestionCountByQuiz(playerQuiz.getQuiz().getId());
    List<PlayerQuizQuestionTypeCount> questionTypeCounts =
        questionService.getCountByQuestionType(playerQuiz.getQuiz().getId());
    return playerQuizMapper.toPlayerQuizDetails(
        playerQuiz, questionCountByQuiz, questionTypeCounts);
  }

  public List<PlayerQuizResponseRecord> getAll(Long userID) {
    return playerQuizMapper.toList(playerQuizService.getAll(userID));
  }

  public PlayerQuizResponseRecord update(PlayerQuizUpdateRequest updateRequest) {
    PlayerQuiz playerQuiz = playerQuizMapper.toEntity(updateRequest);
    return playerQuizMapper.toResponse(playerQuizService.update(playerQuiz));
  }

  public void delete(Long id) {
    playerQuizService.delete(id);
  }

  public PinVerifyResponse verifyPin(PlayerPinRequest playerPinRequest) {
    return playerQuizService.verifyPin(playerPinRequest);
  }
}
