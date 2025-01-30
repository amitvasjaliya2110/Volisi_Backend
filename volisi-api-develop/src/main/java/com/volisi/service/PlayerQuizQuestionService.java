package com.volisi.service;

import com.volisi.entity.PlayerQuizQuestion;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PlayerQuizQuestionService {
  PlayerQuizQuestion add(PlayerQuizQuestion playerQuizQuestion);

  List<PlayerQuizQuestion> getAll();

  PlayerQuizQuestion getById(Long id);

  PlayerQuizQuestion update(PlayerQuizQuestion updatedPlayerQuizQuestion);

  void deleteById(Long id);

  boolean existsByQuestionTypeId(Long id);

  boolean existsByPlayerQuizId(Long id);

  Page<PlayerQuizQuestion> getAllByPlayerQuizId(Long playerId, int page, int pageSize);

  Page<PlayerQuizQuestion> getByPlayerId(Long playerId, int page, int pageSize);
}
