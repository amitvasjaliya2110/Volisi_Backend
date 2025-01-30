package com.volisi.service.impl;

import com.volisi.constant.Constant;
import com.volisi.dto.request.GlobarSearchRequest;
import com.volisi.dto.request.PlayerPinRequest;
import com.volisi.dto.response.PinVerifyResponse;
import com.volisi.entity.PlayerQuiz;
import com.volisi.entity.Quiz;
import com.volisi.enums.ResultCode;
import com.volisi.exception.VolisiAppException;
import com.volisi.mapper.PlayerQuizMapper;
import com.volisi.repository.PlayerQuizRepository;
import com.volisi.service.LeaderboardService;
import com.volisi.service.PlayerQuizQuestionService;
import com.volisi.service.PlayerQuizService;
import com.volisi.service.PlayerService;
import com.volisi.service.QuizService;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class PlayerQuizServiceImpl implements PlayerQuizService {

  private final PlayerQuizRepository playerQuizRepository;
  private final PlayerQuizMapper playerQuizMapper;
  private final MessageSource messageSource;

  private final QuizService quizService;

  private final PlayerQuizQuestionService playerQuizQuestionService;
  private final LeaderboardService leaderboardService;
  private final PlayerService playerService;

  public PlayerQuizServiceImpl(
      PlayerQuizRepository playerQuizRepository,
      PlayerQuizMapper playerQuizMapper,
      MessageSource messageSource,
      @Lazy QuizService quizService,
      PlayerQuizQuestionService playerQuizQuestionService,
      LeaderboardService leaderboardService,
      PlayerService playerService) {
    this.playerQuizRepository = playerQuizRepository;
    this.playerQuizMapper = playerQuizMapper;
    this.messageSource = messageSource;
    this.quizService = quizService;
    this.playerQuizQuestionService = playerQuizQuestionService;
    this.leaderboardService = leaderboardService;
    this.playerService = playerService;
  }

  /**
   * Creates and persists a new PlayerQuiz entity, assigning it a unique code.
   *
   * <p>Before saving the PlayerQuiz entity to the database, this method assigns a unique 6-digit
   * code to the quiz. The code is generated to ensure uniqueness within the specified range.
   *
   * @param playerQuiz The PlayerQuiz entity to be created and persisted.
   * @return The PlayerQuiz entity after being persisted, including the generated unique code.
   */
  @Override
  public PlayerQuiz create(PlayerQuiz playerQuiz) {
    Quiz quiz = quizService.getQuizById(playerQuiz.getQuiz().getId());
    playerQuiz.setQuiz(quiz);
    playerQuiz.setCode(generateUniqueCode());
    playerQuiz.setTime(calculateTotalTime(playerQuiz));
    return playerQuizRepository.save(playerQuiz);
  }

  public PlayerQuiz getById(Long id) {
    return playerQuizRepository
        .findById(id)
        .orElseThrow(
            () ->
                new VolisiAppException(
                    ResultCode.DATA_NOT_FOUND.getCode(),
                    messageSource.getMessage(
                        "foreignkey.notexist",
                        new String[] {Constant.PLAYER_QUIZ},
                        LocaleContextHolder.getLocale())));
  }

  @Override
  public List<PlayerQuiz> getAll(Long userId) {
    return playerQuizRepository.findByUserId(userId);
  }

  @Override
  public PlayerQuiz update(PlayerQuiz playerQuiz) {
    Quiz quiz = quizService.getQuizById(playerQuiz.getQuiz().getId());
    playerQuiz.setQuiz(quiz);
    PlayerQuiz existingEntity = validate(playerQuiz);
    PlayerQuiz updateQuiz = playerQuizMapper.toEntity(playerQuiz, existingEntity);
    updateQuiz.setTime(calculateTotalTime(playerQuiz));
    return playerQuizRepository.save(updateQuiz);
  }

  private PlayerQuiz validate(PlayerQuiz playerQuiz) {
    return getById(playerQuiz.getId());
  }

  /**
   * Generates a unique code for a PlayerQuiz.
   *
   * <p>This method generates a random 6-digit integer to be used as a unique code for identifying a
   * player quiz. It ensures the code is within the range 100000 to 999999.
   *
   * @return a unique 6-digit code.
   */
  private int generateUniqueCode() {
    Random random = new Random();
    return 100000 + random.nextInt(899999);
  }

  public boolean existsByQuizId(Long id) {
    return playerQuizRepository.existsByQuizId(id);
  }

  public void delete(Long id) {
    validate(id);
    playerQuizRepository.deleteById(id);
  }

  private void validate(Long id) {
    boolean existIdPlayerQuizQuestion = playerQuizQuestionService.existsByPlayerQuizId(id);
    boolean existIdPlayer = playerService.existsByPlayerQuizId(id);
    boolean existIdLeaderboard = leaderboardService.existsByPlayerQuizId(id);
    List<String> error = new LinkedList<>();
    if (existIdPlayerQuizQuestion) error.add(Constant.PLAYER_QUIZ_QUESTION);
    if (existIdPlayer) error.add(Constant.PLAYER);
    if (existIdLeaderboard) error.add(Constant.LEADERBOARD);
    if (!ObjectUtils.isEmpty(error)) {
      throw new VolisiAppException(
          ResultCode.DATA_NOT_DELETED.getCode(),
          messageSource.getMessage(
              "data.not.delete",
              new String[] {Constant.PLAYER_QUIZ, StringUtils.join(error, ',')},
              LocaleContextHolder.getLocale()));
    }
  }

  @Override
  public Page<PlayerQuiz> getReportBySearchName(GlobarSearchRequest request) {
    Pageable pageable =
        PageRequest.of(request.page(), request.pageSize(), Sort.by("createdDate").descending());
    return playerQuizRepository.findByNameContainingIgnoreCaseAndUserId(
        request.name(), request.userId(), pageable);
  }

  @Override
  public long calculateTotalTime(PlayerQuiz playerQuiz) {
    if (playerQuiz.getStartDateTime() != null && playerQuiz.getEndDateTime() != null) {
      return Duration.between(playerQuiz.getStartDateTime(), playerQuiz.getEndDateTime())
          .toMinutes();
    }
    return 0;
  }

  @Override
  public PinVerifyResponse verifyPin(PlayerPinRequest player) {
    Optional<PlayerQuiz> playerQuiz =
        playerQuizRepository.findByStatusAndCode("inProgress", player.code());
    if (playerQuiz.isPresent()) {
      return new PinVerifyResponse(playerQuiz.get().getId());
    } else {
      throw new VolisiAppException(
          ResultCode.PIN_VERIFIED_FAILED.getCode(),
          messageSource.getMessage(
              "pin.verified.failed", new Object[] {}, LocaleContextHolder.getLocale()));
    }
  }
}
