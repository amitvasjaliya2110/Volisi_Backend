package com.volisi.service;

import com.volisi.entity.QuestionType;
import java.util.List;

public interface QuestionTypeService {
  QuestionType create(QuestionType questionType);

  List<QuestionType> getAll();

  QuestionType getById(Long id);

  QuestionType update(QuestionType questionType);

  void deleteById(Long id);
}
