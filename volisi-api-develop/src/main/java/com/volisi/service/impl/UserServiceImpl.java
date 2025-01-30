package com.volisi.service.impl;

import com.volisi.constant.Constant;
import com.volisi.entity.User;
import com.volisi.enums.ResultCode;
import com.volisi.exception.VolisiAppException;
import com.volisi.mapper.UserMapper;
import com.volisi.repository.UserRepository;
import com.volisi.service.QuizService;
import com.volisi.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final MessageSource messageSource;
  private final PasswordEncoder passwordEncoder;
  private final QuizService quizService;

  public UserServiceImpl(
      UserRepository userRepository,
      UserMapper userMapper,
      MessageSource messageSource,
      PasswordEncoder passwordEncoder,
      QuizService quizService) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.messageSource = messageSource;
    this.quizService = quizService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User save(User user) {
    duplicateName(user);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public User getById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(
            () ->
                new VolisiAppException(
                    ResultCode.DATA_NOT_FOUND.getCode(),
                    messageSource.getMessage(
                        "foreignkey.notexist",
                        new String[] {Constant.USER},
                        LocaleContextHolder.getLocale())));
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User update(User user) {
    User existingUser = getById(user.getId());
    validate(user);
    User updatedUser = userMapper.toDbEntity(user, existingUser);
    return userRepository.save(updatedUser);
  }

  public User patchUpdate(User user) {
    User existingUser = getById(user.getId());

    Optional.ofNullable(user.getUsername())
        .ifPresent(
            username -> {
              duplicateName(user);
              existingUser.setUsername(username);
            });

    Optional.ofNullable(user.getEmail()).ifPresent(existingUser::setEmail);

    Optional.ofNullable(user.getPassword())
        .ifPresent(password -> existingUser.setPassword(passwordEncoder.encode(password)));

    return userRepository.save(existingUser);
  }

  public void delete(Long id) {
    validate(id);
    userRepository.deleteById(id);
  }

  private void validate(User user) {
    duplicateName(user);
  }

  private void validate(Long id) {
    boolean existingByUserId = quizService.existsByUserId(id);
    if (existingByUserId) {
      throw new VolisiAppException(
          ResultCode.DATA_NOT_DELETED.getCode(),
          messageSource.getMessage(
              "data.not.delete",
              new String[] {Constant.USER, Constant.QUIZ},
              LocaleContextHolder.getLocale()));
    }
  }

  private void duplicateName(User user) {
    User existingName = userRepository.findByUsername(user.getUsername());
    if (existingName != null && !existingName.getId().equals(user.getId())) {
      throw new VolisiAppException(
          ResultCode.USER_ALREADY_EXIST.getCode(),
          messageSource.getMessage(
              "data.exists.error", new String[] {Constant.USER}, LocaleContextHolder.getLocale()));
    }
  }

  public User findByUsername(String userName) {
    return Optional.ofNullable(userRepository.findByUsername(userName))
        .orElseThrow(
            () ->
                new VolisiAppException(
                    ResultCode.USER_NOT_FOUND.getCode(),
                    messageSource.getMessage(
                        "user.not.found", new String[] {}, LocaleContextHolder.getLocale())));
  }
}
