package com.volisi.repository;

import com.volisi.entity.PlayerQuizQuestion;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerQuizQuestionRepository extends JpaRepository<PlayerQuizQuestion, Long> {
  boolean existsByTypeId(Long id);

  boolean existsByPlayerQuizId(Long id);

  List<PlayerQuizQuestion> findByPlayerId(Long id);

  Page<PlayerQuizQuestion> findByPlayerQuizId(Long playerId, Pageable pageable);
}
