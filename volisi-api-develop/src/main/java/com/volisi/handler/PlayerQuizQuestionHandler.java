package com.volisi.handler;

import com.volisi.dto.request.PlayerQuizQuestionRequest;
import com.volisi.dto.request.PlayerQuizQuestionUpdateRequest;
import com.volisi.dto.response.PlayerQuizQuestionAnswerResponse;
import com.volisi.dto.response.PlayerQuizQuestionResponse;
import com.volisi.entity.PlayerQuizQuestion;
import com.volisi.mapper.PlayerQuizQuestionMapper;
import com.volisi.service.PlayerQuizQuestionService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class PlayerQuizQuestionHandler {
  private static final Logger log = LoggerFactory.getLogger(PlayerQuizQuestionHandler.class);
  PlayerQuizQuestionService playerQuizQuestionService;
  PlayerQuizQuestionMapper playerQuizQuestionMapper;

  public PlayerQuizQuestionHandler(
      PlayerQuizQuestionService playerQuizQuestionService,
      PlayerQuizQuestionMapper playerQuizQuestionMapper) {
    this.playerQuizQuestionService = playerQuizQuestionService;
    this.playerQuizQuestionMapper = playerQuizQuestionMapper;
  }

  public PlayerQuizQuestionResponse add(PlayerQuizQuestionRequest playerQuizQuestionRequest) {
    PlayerQuizQuestion playerQuizQuestion =
        playerQuizQuestionMapper.toEntity(playerQuizQuestionRequest);
    return playerQuizQuestionMapper.toResponse(playerQuizQuestionService.add(playerQuizQuestion));
  }

  public List<PlayerQuizQuestionResponse> getAll() {
    return playerQuizQuestionMapper.toList(playerQuizQuestionService.getAll());
  }

  public PlayerQuizQuestionResponse getById(Long id) {
    return playerQuizQuestionMapper.toResponse(playerQuizQuestionService.getById(id));
  }

  public PlayerQuizQuestionResponse update(PlayerQuizQuestionUpdateRequest updateRequest) {
    PlayerQuizQuestion playerQuizQuestion = playerQuizQuestionMapper.toUpdateEntity(updateRequest);
    return playerQuizQuestionMapper.toResponse(
        playerQuizQuestionService.update(playerQuizQuestion));
  }

  public void deleteById(Long id) {
    playerQuizQuestionService.deleteById(id);
  }

  public Page<PlayerQuizQuestionAnswerResponse> getAllByPlayerQuizId(
      Long playerQuizId, int page, int pageSize) {
    Page<PlayerQuizQuestion> questionsPage =
        playerQuizQuestionService.getAllByPlayerQuizId(playerQuizId, page, pageSize);

    List<PlayerQuizQuestionAnswerResponse> questionAnswerResponses =
        playerQuizQuestionMapper.toAnswerList(questionsPage.getContent());
    log.debug("Mapped questions to PlayerQuizQuestionAnswerResponse: {}", questionAnswerResponses);

    Page<PlayerQuizQuestionAnswerResponse> responsePage =
        new PageImpl<>(
            questionAnswerResponses,
            PageRequest.of(page - 1, pageSize),
            questionsPage.getTotalElements());
    log.info(
        "Returning {} questions for playerQuizId: {}",
        responsePage.getTotalElements(),
        playerQuizId);

    return responsePage;
  }

  public Page<PlayerQuizQuestionResponse> getByPlayerId(Long playerId, int page, int pageSize) {
    Page<PlayerQuizQuestion> questionsPage =
        playerQuizQuestionService.getByPlayerId(playerId, page, pageSize);

    List<PlayerQuizQuestionResponse> questionResponses =
        playerQuizQuestionMapper.toList(questionsPage.getContent());
    log.debug("Mapped questions to PlayerQuizQuestionResponse: {}", questionResponses);

    Page<PlayerQuizQuestionResponse> responsePage =
        new PageImpl<>(
            questionResponses,
            PageRequest.of(page - 1, pageSize),
            questionsPage.getTotalElements());
    log.info("Returning {} questions for playerId: {}", responsePage.getTotalElements(), playerId);

    return responsePage;
  }
}
