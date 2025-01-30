package com.volisi.handler;

import com.volisi.dto.request.QuestionTypeRequest;
import com.volisi.dto.request.QuestionTypeUpdateRequest;
import com.volisi.dto.response.QuestionTypeResponse;
import com.volisi.entity.QuestionType;
import com.volisi.mapper.QuestionTypeMapper;
import com.volisi.service.QuestionTypeService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class QuestionTypeHandler {
  private final QuestionTypeService questionTypeService;
  private final QuestionTypeMapper questionTypeMapper;

  public QuestionTypeHandler(
      QuestionTypeService questionTypeService, QuestionTypeMapper questionTypeMapper) {
    this.questionTypeService = questionTypeService;
    this.questionTypeMapper = questionTypeMapper;
  }

  public QuestionTypeResponse create(QuestionTypeRequest questionTypeRequest) {
    QuestionType questionType = questionTypeMapper.toEntity(questionTypeRequest);
    return questionTypeMapper.toResponse(questionTypeService.create(questionType));
  }

  public List<QuestionTypeResponse> getAll() {
    return questionTypeMapper.toList(questionTypeService.getAll());
  }

  public QuestionTypeResponse getById(Long id) {
    return questionTypeMapper.toResponse(questionTypeService.getById(id));
  }

  public QuestionTypeResponse update(QuestionTypeUpdateRequest questionTypeUpdateRequest) {
    QuestionType questionType = questionTypeMapper.toEntity(questionTypeUpdateRequest);
    return questionTypeMapper.toResponse(questionTypeService.update(questionType));
  }

  public void deleteById(Long id) {
    questionTypeService.deleteById(id);
  }
}
