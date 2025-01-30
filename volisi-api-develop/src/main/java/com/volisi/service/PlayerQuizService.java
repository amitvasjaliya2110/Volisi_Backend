package com.volisi.service;

import com.volisi.dto.request.GlobarSearchRequest;
import com.volisi.dto.request.PlayerPinRequest;
import com.volisi.dto.response.PinVerifyResponse;
import com.volisi.entity.PlayerQuiz;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PlayerQuizService {
  PlayerQuiz create(PlayerQuiz playerQuiz);

  PlayerQuiz getById(Long id);

  List<PlayerQuiz> getAll(Long userId);

  PlayerQuiz update(PlayerQuiz playerQuiz);

  void delete(Long id);

  boolean existsByQuizId(Long id);

  Page<PlayerQuiz> getReportBySearchName(GlobarSearchRequest request);

  long calculateTotalTime(PlayerQuiz playerQuiz);

  PinVerifyResponse verifyPin(PlayerPinRequest playerPinRequest);
}
