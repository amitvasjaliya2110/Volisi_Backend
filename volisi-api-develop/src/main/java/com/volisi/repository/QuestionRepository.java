package com.volisi.repository;

import com.volisi.entity.Question;
import com.volisi.projection.PlayerQuizQuestionTypeCount;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  boolean existsByAnswerOptionId(Long id);

  boolean existsByQuizId(Long id);

  Page<Question> findByQuizId(Long quizId, Pageable pageable);

  Long countByQuizId(Long quizId);

  @Query(
      value =
          """
        SELECT qt.name AS QuestionTypeName, COUNT(*) AS QuestionCount
        FROM question q
        RIGHT JOIN question_type qt ON q.question_type_id = qt.id
        WHERE q.quiz_id = :quizId
        GROUP BY qt.name
        """,
      nativeQuery = true)
  List<PlayerQuizQuestionTypeCount> countByQuestionType(@Param("quizId") Long quizId);
}
