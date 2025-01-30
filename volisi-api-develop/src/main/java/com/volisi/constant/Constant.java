package com.volisi.constant;

public class Constant {
  public static String QUIZ = "Quiz";
  public static String PLAYER = "Player";
  public static String COLLECTION = "Collection";
  public static String QUESTION_TYPE = "QuestionType";
  public static String POINTS_CONFIG = "Points";
  public static final String QUESTION = "Question and QuestionAnswers";
  public static final String QUESTIONS = "Questions and QuestionAnswers";
  public static String LEADERBOARD = "Leaderboard";
  public static String ANSWER_OPTION = "AnswerOption";
  public static String USER = "User";
  public static String AUTHENTICATION = "Authentication";
  public static String PLAYER_QUIZ = "Player Quiz";
  public static String PLAYER_QUIZ_QUESTION = "Player Quiz Question and Answer";
  public static String ERROR_CODE_PREFIX = "Error Occur";
  public static String CONSTRAINT_ERROR = "Constraint Violation Error";
  public static String VALIDATION_ERROR = "Validation Error";
  public static String ILLEGAL_ARGUMENT_ERROR = "Illegal Argument Error";
  public static final String QUIZ_STATUS_CREATED = "CREATED";
  public static final String QUIZ_STATUS_DRAFT = "DRAFT";

  public static final String GLOBAL_SEARCH_QUIZ = "quiz";
  public static final String GLOBAL_SEARCH_REPORT = "report";

  public static final String[] WHITE_LIST_URL = {
    "/api/auth/login",
    "/api/user/**",
    "/api/auth/otp/generate/**",
    "api/user/username/**",
    "/api/auth/otp/verify"
  };
  public static String NON_WHITE_LIST_URL = "/api/**";
  public static String AUTHORIZATION = "Authorization";
  public static String BEARER = "Bearer";
}
