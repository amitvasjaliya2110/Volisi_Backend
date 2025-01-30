package com.volisi.service;

import com.volisi.dto.request.GlobarSearchRequest;
import com.volisi.dto.request.QuizPaginationRequest;
import com.volisi.dto.request.QuizRequestByStatus;
import com.volisi.entity.Quiz;
import java.util.List;
import org.springframework.data.domain.Page;

public interface QuizService {
  Quiz save(Quiz quiz);

  Quiz getQuizById(Long id);

  List<Quiz> getAll();

  Quiz update(Quiz quiz);

  void delete(Long id);

  boolean existsByUserId(Long id);

  boolean existsByCollectionId(Long id);

  Page<Quiz> getQuizzesByCollectionAndUser(QuizPaginationRequest request);

  Page<Quiz> getQuizzesByUserAndStatus(QuizRequestByStatus quizRequestbyStatus);

  Page<Quiz> getQuizBySearchName(GlobarSearchRequest request);
}
