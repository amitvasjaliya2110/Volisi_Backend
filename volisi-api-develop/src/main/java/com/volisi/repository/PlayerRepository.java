package com.volisi.repository;

import com.volisi.entity.Player;
import com.volisi.projection.PlayerQuizQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
  Player findByName(String name);

  boolean existsByPlayerQuizId(Long id);

  @Query(
      value =
          "SELECT p.id, p.name, p.final_score AS FinalScore,p.ranking, "
              + "SUM(CASE WHEN pqq.is_correct = true THEN 1 ELSE 0 END) AS TotalCorrectAnswers, "
              + "SUM(CASE WHEN pqq.no_answer = true THEN 1 ELSE 0 END) AS TotalUnansweredQuestions, "
              + "SUM(pqq.time_taken) AS TotalTime "
              + "FROM player p "
              + "JOIN player_quiz_question pqq ON p.id = pqq.player_id "
              + "WHERE pqq.player_quiz_id = :playerQuizId "
              + "GROUP BY p.id ",
      nativeQuery = true)
  Page<PlayerQuizQuestion> findByPlayerQuizId(
      @Param("playerQuizId") Long playerQuizId, Pageable pageable);
}
