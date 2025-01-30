package com.volisi.repository;

import com.volisi.entity.PlayerQuiz;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerQuizRepository extends JpaRepository<PlayerQuiz, Long> {

  boolean existsByQuizId(Long id);

  Optional<PlayerQuiz> findByStatusAndCode(String status, int code);

  Page<PlayerQuiz> findByNameContainingIgnoreCaseAndUserId(
      String name, Long userId, Pageable pageable);

  List<PlayerQuiz> findByUserId(Long userId);
}
