package com.volisi.dto;

public record BaseResponse<T>(int resultCode, String resultMessage, T data) {
  public BaseResponse(int resultCode, String resultMessage) {
    this(resultCode, resultMessage, null);
  }
}
