package com.volisi.service.impl;

import com.volisi.constant.Constant;
import com.volisi.entity.QuestionType;
import com.volisi.enums.ResultCode;
import com.volisi.exception.VolisiAppException;
import com.volisi.mapper.QuestionTypeMapper;
import com.volisi.repository.QuestionTypeRepository;
import com.volisi.service.QuestionTypeService;
import com.volisi.service.QuizService;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {

  private final QuestionTypeRepository questionTypeRepository;
  private final QuestionTypeMapper questionTypeMapper;
  private final MessageSource messageSource;

  private final QuizService quizService;

  public QuestionTypeServiceImpl(
      QuestionTypeRepository questionTypeRepository,
      QuestionTypeMapper questionTypeMapper,
      MessageSource messageSource,
      @Lazy QuizService quizService) {
    this.questionTypeRepository = questionTypeRepository;
    this.questionTypeMapper = questionTypeMapper;
    this.messageSource = messageSource;
    this.quizService = quizService;
  }

  @Override
  public QuestionType create(QuestionType questionType) {
    duplicateName(questionType);
    return questionTypeRepository.save(questionType);
  }

  @Override
  public List<QuestionType> getAll() {
    return questionTypeRepository.findAll();
  }

  public QuestionType getById(Long id) {
    return questionTypeRepository
        .findById(id)
        .orElseThrow(
            () ->
                new VolisiAppException(
                    ResultCode.DATA_NOT_FOUND.getCode(),
                    messageSource.getMessage(
                        "foreignkey.notexist",
                        new String[] {Constant.QUESTION_TYPE},
                        LocaleContextHolder.getLocale())));
  }

  @Override
  public QuestionType update(QuestionType questionType) {
    QuestionType existingEntity = getById(questionType.getId());
    validate(questionType);
    QuestionType validEntity = questionTypeMapper.toDbEntity(questionType, existingEntity);
    return questionTypeRepository.save(validEntity);
  }

  private void validate(QuestionType questionType) {
    duplicateName(questionType);
  }

  private void duplicateName(QuestionType questionType) {
    QuestionType existingQuiTypeWithName =
        questionTypeRepository.findByName(questionType.getName());
    if (existingQuiTypeWithName != null
        && !existingQuiTypeWithName.getId().equals(questionType.getId())) {
      throw new VolisiAppException(
          ResultCode.QUIZ_ALREADY_EXIST.getCode(),
          messageSource.getMessage(
              "data.exists.error",
              new String[] {Constant.QUESTION_TYPE},
              LocaleContextHolder.getLocale()));
    }
  }

  public void deleteById(Long id) {
    questionTypeRepository.deleteById(id);
  }
}
