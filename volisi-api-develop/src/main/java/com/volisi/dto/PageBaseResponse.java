package com.volisi.dto;

import java.util.List;

public record PageBaseResponse<T>(
    int resultCode,
    String resultMessage,
    int page,
    int pageSize,
    long totalElements,
    List<T> data) {
  public PageBaseResponse(
      int resultCode,
      String resultMessage,
      int page,
      int pageSize,
      long totalElements,
      List<T> data) {
    this.resultCode = resultCode;
    this.resultMessage = resultMessage;
    this.page = page;
    this.pageSize = pageSize;
    this.totalElements = totalElements;
    this.data = data;
  }
}
