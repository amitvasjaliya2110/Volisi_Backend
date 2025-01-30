package com.volisi.handler;

import com.volisi.dto.request.QuestionRequest;
import com.volisi.dto.request.QuestionUpdateRequest;
import com.volisi.dto.response.QuestionResponse;
import com.volisi.entity.Question;
import com.volisi.mapper.QuestionMapper;
import com.volisi.service.QuestionService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class QuestionHandler {
  private static final Logger log = LoggerFactory.getLogger(QuestionHandler.class);
  private final QuestionService questionService;
  private final QuestionMapper questionMapper;

  public QuestionHandler(QuestionService questionService, QuestionMapper questionMapper) {

    this.questionService = questionService;
    this.questionMapper = questionMapper;
  }

  public QuestionResponse add(QuestionRequest questionRequest) {
    Question question = questionMapper.toEntity(questionRequest);
    return questionMapper.toResponse(questionService.add(question));
  }

  public List<QuestionResponse> getAll() {
    return questionMapper.toList(questionService.getAll());
  }

  public QuestionResponse getById(Long id) {
    return questionMapper.toResponse(questionService.getById(id));
  }

  public QuestionResponse update(QuestionUpdateRequest questionUpdateRequest) {
    Question question = questionMapper.toEntity(questionUpdateRequest);
    return questionMapper.toResponse(questionService.update(question));
  }

  public void delete(Long id) {
    questionService.delete(id);
  }

  public Page<QuestionResponse> getQuestionsByQuizId(Long quizId, int page, int pageSize) {
    Page<Question> questionsPage = questionService.getQuestionsByQuizId(quizId, page, pageSize);

    List<QuestionResponse> questionResponses = questionMapper.toList(questionsPage.getContent());
    log.debug("Mapped questions to QuestionResponse: {}", questionResponses);

    Page<QuestionResponse> responsePage =
        new PageImpl<>(
            questionResponses,
            PageRequest.of(page - 1, pageSize),
            questionsPage.getTotalElements());
    log.info("Returning {} questions for quizId: {}", responsePage.getTotalElements(), quizId);

    return responsePage;
  }
}
