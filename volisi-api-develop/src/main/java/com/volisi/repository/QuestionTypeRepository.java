package com.volisi.repository;

import com.volisi.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
  QuestionType findByName(String name);
}
