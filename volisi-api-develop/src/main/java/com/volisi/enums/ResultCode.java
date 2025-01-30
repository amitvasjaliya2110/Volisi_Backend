package com.volisi.enums;

import lombok.Getter;

/*
 * Custom result codes for more specific and descriptive information.
 */
@Getter
public enum ResultCode {
  SUCCESS(1001),
  USER_ALREADY_EXIST(1101, "User Already Exists"),
  QUIZ_ALREADY_EXIST(1102, "Quiz Already Exists"),
  PLAYER_ALREADY_EXIST(1103, "Player Already Exists"),
  ANSWER_OPTION_ALREADY_EXIST(1104, "Answer option Already Exist"),
  PLAYER_QUIZ_KEY_NOT_EXIST(1201, "Player Quiz will given id does not exist."),
  QUESTION_QUIZ_KEY_NOT_EXIST(1202, "Question Type will given id does not exist."),
  DATA_NOT_FOUND(1301, "Data Not Found."),
  DATA_NOT_DELETED(1302, "Data cannot be deleted"),
  USER_NOT_FOUND(1303, "User Not Found"),
  REFRESH_TOKEN_EXCEPTION(1401, "Not able to generate Refresh Token"),
  OTP_VERIFIED_FAILED(1501, "Incorrect OTP"),
  PIN_VERIFIED_FAILED(1502, "Incorrect PIN"),
  QUIZ_NOT_FOUND(1304, "Quiz Not Found");

  private final int code;
  private String message;

  ResultCode(int code) {
    this.code = code;
  }

  ResultCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCustomMessage(String dynamicData) {
    return String.format(message, dynamicData);
  }
}
