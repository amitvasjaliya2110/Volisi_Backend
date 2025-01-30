package com.volisi.service.impl;

import com.volisi.constant.Constant;
import com.volisi.entity.AnswerOption;
import com.volisi.entity.Question;
import com.volisi.entity.QuestionType;
import com.volisi.entity.Quiz;
import com.volisi.enums.ResultCode;
import com.volisi.exception.VolisiAppException;
import com.volisi.mapper.QuestionMapper;
import com.volisi.projection.PlayerQuizQuestionTypeCount;
import com.volisi.repository.QuestionRepository;
import com.volisi.service.AnswerOptionService;
import com.volisi.service.QuestionService;
import com.volisi.service.QuestionTypeService;
import com.volisi.service.QuizService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {
  private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);
  private final QuestionRepository questionRepository;
  private final MessageSource messageSource;
  private final QuestionMapper questionMapper;

  private final QuizService quizService;
  private final AnswerOptionService answerOptionService;
  private final QuestionTypeService questionTypeService;

  public QuestionServiceImpl(
      QuestionRepository questionRepository,
      MessageSource messageSource,
      QuestionMapper questionMapper,
      @Lazy QuizService quizService,
      AnswerOptionService answerOptionService,
      QuestionTypeService questionTypeService) {
    this.questionRepository = questionRepository;
    this.messageSource = messageSource;
    this.questionMapper = questionMapper;
    this.quizService = quizService;
    this.answerOptionService = answerOptionService;
    this.questionTypeService = questionTypeService;
  }

  @Override
  public Question add(Question question) {
    Quiz quiz = quizService.getQuizById(question.getQuiz().getId());
    question.setQuiz(quiz);
    Optional.ofNullable(question.getAnswerOption())
        .ifPresent(
            answerOption -> {
              AnswerOption fetchedOption = answerOptionService.getById(answerOption.getId());
              question.setAnswerOption(fetchedOption);
            });
    Optional.ofNullable(question.getQuestionType())
        .ifPresent(
            questionType -> {
              QuestionType fetchedOption = questionTypeService.getById(questionType.getId());
              question.setQuestionType(fetchedOption);
            });
    question.setPoints(1000);
    return questionRepository.save(question);
  }

  @Override
  public List<Question> getAll() {
    return questionRepository.findAll();
  }

  public Question getById(Long id) {
    return questionRepository
        .findById(id)
        .orElseThrow(
            () ->
                new VolisiAppException(
                    ResultCode.DATA_NOT_FOUND.getCode(),
                    messageSource.getMessage(
                        "foreignkey.notexist",
                        new String[] {Constant.QUESTION},
                        LocaleContextHolder.getLocale())));
  }

  public Question update(Question question) {
    Question existingQuestion = getById(question.getId());
    Quiz quiz = quizService.getQuizById(question.getQuiz().getId());
    question.setQuiz(quiz);
    Optional.ofNullable(question.getAnswerOption())
        .ifPresent(
            answerOption -> {
              AnswerOption fetchedOption = answerOptionService.getById(answerOption.getId());
              question.setAnswerOption(fetchedOption);
            });
    Optional.ofNullable(question.getQuestionType())
        .ifPresent(
            questionType -> {
              QuestionType fetchedOption = questionTypeService.getById(questionType.getId());
              question.setQuestionType(fetchedOption);
            });
    question.setPoints(1000);
    Question updatedQuestion = questionMapper.toDbEntity(question, existingQuestion);
    return questionRepository.save(updatedQuestion);
  }

  public void delete(Long id) {
    questionRepository.deleteById(id);
  }

  public boolean existsByQuestionTypeId(Long id) {
    return questionRepository.existsByAnswerOptionId(id);
  }

  public boolean existsByQuizId(Long id) {
    return questionRepository.existsByQuizId(id);
  }

  @Override
  public Page<Question> getQuestionsByQuizId(Long quizId, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page - 1, pageSize);
    Page<Question> questionsPage = questionRepository.findByQuizId(quizId, pageable);
    log.debug("Fetched questions: {}", questionsPage.getContent());
    log.info("Returning {} questions for quizId: {}", questionsPage.getTotalElements(), quizId);
    return questionsPage;
  }

  public Long getQuestionCountByQuiz(Long quizId) {
    return questionRepository.countByQuizId(quizId);
  }

  public List<PlayerQuizQuestionTypeCount> getCountByQuestionType(Long quizId) {
    return questionRepository.countByQuestionType(quizId);
  }
}
