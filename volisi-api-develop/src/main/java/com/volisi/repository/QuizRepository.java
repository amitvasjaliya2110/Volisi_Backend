package com.volisi.repository;

import com.volisi.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
  Quiz findByName(String name);

  boolean existsByCollectionId(Long id);

  boolean existsByUserId(Long id);

  Page<Quiz> findByNameContainingIgnoreCaseAndUserId(String name, Long userId, Pageable pageable);

  Page<Quiz> findByCollectionIdAndUserId(Long collectionId, Long userId, Pageable pageable);

  Page<Quiz> findByUserIdAndStatus(Long userId, String status, Pageable pageable);
}
