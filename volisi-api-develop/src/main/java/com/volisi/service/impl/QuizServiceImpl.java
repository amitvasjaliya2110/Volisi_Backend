package com.volisi.service.impl;

import com.volisi.constant.Constant;
import com.volisi.dto.request.GlobarSearchRequest;
import com.volisi.dto.request.QuizPaginationRequest;
import com.volisi.dto.request.QuizRequestByStatus;
import com.volisi.entity.Collection;
import com.volisi.entity.Quiz;
import com.volisi.entity.User;
import com.volisi.enums.ResultCode;
import com.volisi.exception.VolisiAppException;
import com.volisi.mapper.QuizMapper;
import com.volisi.repository.QuizRepository;
import com.volisi.service.CollectionService;
import com.volisi.service.PlayerQuizService;
import com.volisi.service.QuestionService;
import com.volisi.service.QuizService;
import com.volisi.service.UserService;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/** Service implementation for managing quiz-related operations. */
@Service
public class QuizServiceImpl implements QuizService {
  private final QuizRepository repository;
  private final QuizMapper quizMapper;
  private final MessageSource messageSource;
  private final UserService userService;
  private final CollectionService collectionService;
  private final QuestionService questionService;
  private final PlayerQuizService playerQuizService;

  /**
   * Constructs a new QuizServiceImpl with the specified QuizRepository dependency.
   *
   * @param repository The QuizRepository responsible for interacting with the database.
   */
  public QuizServiceImpl(
      QuizRepository repository,
      QuizMapper quizMapper,
      MessageSource messageSource,
      @Lazy UserService userService,
      @Lazy CollectionService collectionService,
      QuestionService questionService,
      PlayerQuizService playerQuizService) {
    this.repository = repository;
    this.quizMapper = quizMapper;
    this.messageSource = messageSource;
    this.userService = userService;
    this.collectionService = collectionService;
    this.questionService = questionService;
    this.playerQuizService = playerQuizService;
  }

  /**
   * Saves a quiz if no quiz with the same name already exists.
   *
   * @param quiz The quiz entity to be saved.
   * @return The saved quiz entity.
   * @throws IllegalArgumentException if a quiz with the same name already exists.
   */
  public void getReferenceId(Quiz quiz) {
    User user = userService.getById(quiz.getUser().getId());
    quiz.setUser(user);
    if (Objects.nonNull(quiz.getCollection())) {
      Collection collection = collectionService.getById(quiz.getCollection().getId());
      quiz.setCollection(collection);
    }
  }

  @Override
  public Quiz save(Quiz quiz) {
    getReferenceId(quiz);

    Quiz existingName = repository.findByName(quiz.getName());
    if (existingName != null) {
      throw new VolisiAppException(
          ResultCode.QUIZ_ALREADY_EXIST.getCode(),
          messageSource.getMessage(
              "data.exists.error", new String[] {Constant.QUIZ}, LocaleContextHolder.getLocale()));
    }
    return repository.save(quiz);
  }

  /**
   * Retrieves a quiz by its ID.
   *
   * @param id The ID of the quiz to retrieve.
   * @return The quiz entity if found, otherwise null.
   */
  @Override
  public Quiz getQuizById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new VolisiAppException(
                    ResultCode.DATA_NOT_FOUND.getCode(),
                    messageSource.getMessage(
                        "foreignkey.notexist",
                        new String[] {Constant.QUIZ},
                        LocaleContextHolder.getLocale())));
  }

  @Override
  public List<Quiz> getAll() {
    return repository.findAll();
  }

  @Override
  public Page<Quiz> getQuizzesByCollectionAndUser(QuizPaginationRequest request) {
    userService.getById(request.userId());
    collectionService.getById(request.collectionId());
    if (!existsByUserId(request.userId()) || !existsByCollectionId(request.collectionId())) {
      throw new VolisiAppException(
          ResultCode.QUIZ_NOT_FOUND.getCode(),
          messageSource.getMessage(
              "quiz.not.found",
              new String[] {Constant.USER, Constant.QUIZ},
              LocaleContextHolder.getLocale()));
    }
    return repository.findByCollectionIdAndUserId(
        request.collectionId(),
        request.userId(),
        PageRequest.of(request.pageNo(), request.pageSize()));
  }

  @Override
  public Page<Quiz> getQuizBySearchName(GlobarSearchRequest request) {
    Pageable pageable =
        PageRequest.of(request.page(), request.pageSize(), Sort.by("createdDate").descending());
    return repository.findByNameContainingIgnoreCaseAndUserId(
        request.name(), request.userId(), pageable);
  }

  @Override
  public Quiz update(Quiz quiz) {
    getReferenceId(quiz);
    Quiz existingEntity = getQuizById(quiz.getId());
    validate(quiz);
    Quiz validEntity = quizMapper.toEntity(quiz, existingEntity);
    return repository.save(validEntity);
  }

  public boolean existsByUserId(Long id) {
    return repository.existsByUserId(id);
  }

  private void validate(Quiz quiz) {
    duplicateName(quiz);
  }

  private void duplicateName(Quiz quiz) {
    Quiz existingQuizWithName = repository.findByName(quiz.getName());
    if (existingQuizWithName != null && !existingQuizWithName.getId().equals(quiz.getId())) {
      throw new VolisiAppException(
          ResultCode.QUIZ_ALREADY_EXIST.getCode(),
          messageSource.getMessage(
              "data.exists.error", new String[] {Constant.QUIZ}, LocaleContextHolder.getLocale()));
    }
  }

  @Override
  public void delete(Long id) {
    validate(id);
    repository.deleteById(id);
  }

  private void validate(Long id) {
    boolean existIdQuestion = questionService.existsByQuizId(id);
    boolean existIdPlayerQuiz = playerQuizService.existsByQuizId(id);

    List<String> error = new LinkedList<>();
    if (existIdQuestion) error.add(Constant.QUESTION);
    if (existIdPlayerQuiz) error.add(Constant.PLAYER_QUIZ);

    if (!ObjectUtils.isEmpty(error)) {
      throw new VolisiAppException(
          ResultCode.DATA_NOT_DELETED.getCode(),
          messageSource.getMessage(
              "data.not.delete",
              new String[] {Constant.QUIZ, StringUtils.join(error, ',')},
              LocaleContextHolder.getLocale()));
    }
  }

  public boolean existsByCollectionId(Long id) {
    return repository.existsByCollectionId(id);
  }

  @Override
  public Page<Quiz> getQuizzesByUserAndStatus(QuizRequestByStatus quizRequestbyStatus) {
    userService.getById(quizRequestbyStatus.userId());

    if (!existsByUserId(quizRequestbyStatus.userId())) {
      throw new VolisiAppException(
          ResultCode.QUIZ_NOT_FOUND.getCode(),
          messageSource.getMessage(
              "quiz.not.found", new String[] {}, LocaleContextHolder.getLocale()));
    }
    Pageable pageable =
        PageRequest.of(
            quizRequestbyStatus.pageNo(),
            quizRequestbyStatus.pageSize(),
            Sort.by(Sort.Direction.DESC, "id"));

    return repository.findByUserIdAndStatus(
        quizRequestbyStatus.userId(), quizRequestbyStatus.status(), pageable);
  }
}
