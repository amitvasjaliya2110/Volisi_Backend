package com.volisi.service;

import com.volisi.entity.Player;
import com.volisi.projection.PlayerQuizQuestion;
import org.springframework.data.domain.Page;

public interface PlayerService {
  Player create(Player player);

  Page<PlayerQuizQuestion> getPlayerByPlayerQuiz(Long playerQuizId, int page, int pageSize);

  Player getById(Long id);

  Player update(Player player);

  void delete(Long id);

  boolean existsByPlayerQuizId(Long id);
}
