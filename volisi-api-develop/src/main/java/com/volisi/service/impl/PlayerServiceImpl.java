package com.volisi.service.impl;

import com.volisi.constant.Constant;
import com.volisi.entity.Player;
import com.volisi.entity.PlayerQuiz;
import com.volisi.enums.ResultCode;
import com.volisi.exception.VolisiAppException;
import com.volisi.projection.PlayerQuizQuestion;
import com.volisi.repository.PlayerQuizQuestionRepository;
import com.volisi.repository.PlayerRepository;
import com.volisi.service.LeaderboardService;
import com.volisi.service.PlayerQuizService;
import com.volisi.service.PlayerService;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {
  private static final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);
  private final PlayerRepository playerRepository;
  private final MessageSource messageSource;
  private final PlayerQuizService playerQuizService;
  private final LeaderboardService leaderboardService;

  public PlayerServiceImpl(
      PlayerRepository playerRepository,
      MessageSource messageSource,
      @Lazy PlayerQuizService playerQuizService,
      LeaderboardService leaderboardService,
      PlayerQuizQuestionRepository playerQuizQuestionRepository) {
    this.playerRepository = playerRepository;
    this.messageSource = messageSource;
    this.playerQuizService = playerQuizService;
    this.leaderboardService = leaderboardService;
  }

  public void getReferenceId(Player player) {
    PlayerQuiz playerQuiz = playerQuizService.getById(player.getPlayerQuiz().getId());
    player.setPlayerQuiz(playerQuiz);
  }

  public Player create(Player player) {
    getReferenceId(player);
    Player existingName = playerRepository.findByName(player.getName());
    if (existingName != null) {
      throw new VolisiAppException(
          ResultCode.PLAYER_ALREADY_EXIST.getCode(),
          messageSource.getMessage(
              "data.exists.error", new String[] {Constant.PLAYER}, Locale.getDefault()));
    }
    return playerRepository.save(player);
  }

  @Override
  public Page<PlayerQuizQuestion> getPlayerByPlayerQuiz(Long playerQuizId, int page, int pageSize) {
    Page<PlayerQuizQuestion> playerPage =
        playerRepository.findByPlayerQuizId(playerQuizId, PageRequest.of(page - 1, pageSize));
    log.debug("Fetched Players: {}", playerPage);
    return playerPage;
  }

  public Player getById(Long id) {
    return playerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new VolisiAppException(
                    ResultCode.DATA_NOT_FOUND.getCode(),
                    messageSource.getMessage(
                        "foreignkey.notexist",
                        new String[] {Constant.PLAYER},
                        LocaleContextHolder.getLocale())));
  }

  @Override
  public Player update(Player player) {
    getReferenceId(player);
    Player existingPlayer = getById(player.getId());
    validate(player);
    existingPlayer.setName(player.getName());
    existingPlayer.setPlayerQuiz(player.getPlayerQuiz());
    return playerRepository.save(existingPlayer);
  }

  private void validate(Player player) {
    duplicateName(player);
  }

  private void duplicateName(Player player) {
    Player existingPlayerWithName = playerRepository.findByName(player.getName());
    if (existingPlayerWithName != null && !existingPlayerWithName.getId().equals(player.getId())) {
      throw new VolisiAppException(
          ResultCode.PLAYER_ALREADY_EXIST.getCode(),
          messageSource.getMessage(
              "data.exists.error",
              new String[] {Constant.PLAYER},
              LocaleContextHolder.getLocale()));
    }
  }

  public void delete(Long id) {
    validate(id);
    playerRepository.deleteById(id);
  }

  public void validate(Long id) {
    boolean existsByPlayerId = leaderboardService.existsByPlayerId(id);

    if (existsByPlayerId) {
      throw new VolisiAppException(
          ResultCode.DATA_NOT_DELETED.getCode(),
          messageSource.getMessage(
              "data.not.delete",
              new String[] {Constant.PLAYER, Constant.LEADERBOARD},
              LocaleContextHolder.getLocale()));
    }
  }

  public boolean existsByPlayerQuizId(Long id) {
    return playerRepository.existsByPlayerQuizId(id);
  }
}
