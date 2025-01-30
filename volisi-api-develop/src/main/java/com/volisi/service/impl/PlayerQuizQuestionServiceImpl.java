package com.volisi.service.impl;

import com.volisi.constant.Constant;
import com.volisi.entity.AnswerOption;
import com.volisi.entity.PlayerQuiz;
import com.volisi.entity.PlayerQuizQuestion;
import com.volisi.enums.ResultCode;
import com.volisi.exception.VolisiAppException;
import com.volisi.mapper.PlayerQuizQuestionMapper;
import com.volisi.repository.PlayerQuizQuestionRepository;
import com.volisi.service.AnswerOptionService;
import com.volisi.service.PlayerQuizQuestionService;
import com.volisi.service.PlayerQuizService;
import com.volisi.service.PlayerService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlayerQuizQuestionServiceImpl implements PlayerQuizQuestionService {
  private static final Logger log = LoggerFactory.getLogger(PlayerQuizQuestionServiceImpl.class);
  private final PlayerQuizQuestionRepository playerQuizQuestionRepository;
  private final PlayerQuizService playerQuizService;
  private final AnswerOptionService answerOptionService;
  private final PlayerQuizQuestionMapper playerQuizQuestionMapper;
  private final MessageSource messageSource;
  private final PlayerService playerService;

  public PlayerQuizQuestionServiceImpl(
      PlayerQuizQuestionRepository playerQuizQuestionRepository,
      @Lazy PlayerQuizService playerQuizService,
      AnswerOptionService answerOptionService,
      PlayerQuizQuestionMapper playerQuizQuestionMapper,
      MessageSource messageSource,
      PlayerService playerService) {
    this.playerQuizQuestionRepository = playerQuizQuestionRepository;
    this.playerQuizService = playerQuizService;
    this.answerOptionService = answerOptionService;
    this.playerQuizQuestionMapper = playerQuizQuestionMapper;
    this.messageSource = messageSource;
    this.playerService = playerService;
  }

  @Override
  public PlayerQuizQuestion add(PlayerQuizQuestion playerQuizQuestion) {
    PlayerQuiz playerQuiz = playerQuizService.getById(playerQuizQuestion.getPlayerQuiz().getId());
    AnswerOption answerOptionOptional =
        answerOptionService.getById(playerQuizQuestion.getType().getId());

    playerQuizQuestion.setPlayerQuiz(playerQuiz);
    playerQuizQuestion.setType(answerOptionOptional);

    return playerQuizQuestionRepository.save(playerQuizQuestion);
  }

  @Override
  public List<PlayerQuizQuestion> getAll() {
    return playerQuizQuestionRepository.findAll();
  }

  public PlayerQuizQuestion getById(Long id) {
    return playerQuizQuestionRepository
        .findById(id)
        .orElseThrow(
            () ->
                new VolisiAppException(
                    ResultCode.DATA_NOT_FOUND.getCode(),
                    messageSource.getMessage(
                        "foreignkey.notexist",
                        new String[] {Constant.PLAYER_QUIZ_QUESTION},
                        LocaleContextHolder.getLocale())));
  }

  @Override
  public PlayerQuizQuestion update(PlayerQuizQuestion updateRequest) {
    PlayerQuizQuestion existingPlayerQuizQuestion = getById(updateRequest.getId());
    PlayerQuiz playerQuiz = playerQuizService.getById(updateRequest.getPlayerQuiz().getId());
    AnswerOption answerOption = answerOptionService.getById(updateRequest.getType().getId());

    updateRequest.setPlayerQuiz(playerQuiz);
    updateRequest.setType(answerOption);

    PlayerQuizQuestion updatedRequest =
        playerQuizQuestionMapper.toDbEntity(updateRequest, existingPlayerQuizQuestion);
    return playerQuizQuestionRepository.save(updatedRequest);
  }

  @Override
  public boolean existsByQuestionTypeId(Long id) {
    return playerQuizQuestionRepository.existsByTypeId(id);
  }

  public boolean existsByPlayerQuizId(Long id) {
    return playerQuizQuestionRepository.existsByPlayerQuizId(id);
  }

  @Override
  public void deleteById(Long id) {
    playerQuizQuestionRepository.deleteById(id);
  }

  public Page<PlayerQuizQuestion> getAllByPlayerQuizId(Long playerId, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page - 1, pageSize);
    Long id = playerQuizService.getById(playerId).getId();
    Page<PlayerQuizQuestion> questionsPage =
        playerQuizQuestionRepository.findByPlayerQuizId(id, pageable);
    log.debug("Fetched Player Quiz Question: {}", questionsPage.getContent());
    log.info(
        "Returning {} questions and answer for playerQuizId: {}",
        questionsPage.getTotalElements(),
        playerId);
    return questionsPage;
  }

  @Override
  public Page<PlayerQuizQuestion> getByPlayerId(Long playerId, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page - 1, pageSize);
    Long id = playerService.getById(playerId).getPlayerQuiz().getId();
    Page<PlayerQuizQuestion> questionsPage =
        playerQuizQuestionRepository.findByPlayerQuizId(id, pageable);
    log.debug("Fetched Player Quiz Question: {}", questionsPage.getContent());
    log.info("Returning {} questions for playerId: {}", questionsPage.getTotalElements(), playerId);
    return questionsPage;
  }
}
