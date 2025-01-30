package com.volisi.exception;

import lombok.Getter;

@Getter
public class VolisiAppException extends RuntimeException {
  private final int resultCode;
  private final String resultMessage;

  public VolisiAppException(int resultCode, String resultMessage) {
    this.resultCode = resultCode;
    this.resultMessage = resultMessage;
  }
}
