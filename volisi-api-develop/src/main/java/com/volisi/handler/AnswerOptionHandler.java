package com.volisi.handler;

import com.volisi.dto.request.AnswerOptionRequest;
import com.volisi.dto.request.AnswerOptionUpdateRequest;
import com.volisi.dto.response.AnswerOptionResponse;
import com.volisi.entity.AnswerOption;
import com.volisi.mapper.AnswerOptionMapper;
import com.volisi.service.AnswerOptionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AnswerOptionHandler {

  private final AnswerOptionService answerOptionService;
  private final AnswerOptionMapper answerOptionMapper;

  public AnswerOptionHandler(
      AnswerOptionService answerOptionService, AnswerOptionMapper answerOptionMapper) {
    this.answerOptionService = answerOptionService;
    this.answerOptionMapper = answerOptionMapper;
  }

  public AnswerOptionResponse create(AnswerOptionRequest answerOptionRequest) {
    AnswerOption answerOption = answerOptionMapper.toEntity(answerOptionRequest);
    return answerOptionMapper.toResponse(answerOptionService.create(answerOption));
  }

  public List<AnswerOptionResponse> getAll() {
    return answerOptionMapper.toList(answerOptionService.getAll());
  }

  public AnswerOptionResponse getById(Long id) {
    return answerOptionMapper.toResponse(answerOptionService.getById(id));
  }

  public AnswerOptionResponse update(@Valid AnswerOptionUpdateRequest answerOptionUpdateRequest) {
    AnswerOption answerOption = answerOptionMapper.toEntity(answerOptionUpdateRequest);
    return answerOptionMapper.toResponse(answerOptionService.update(answerOption));
  }

  public void delete(Long id) {
    answerOptionService.delete(id);
  }
}
