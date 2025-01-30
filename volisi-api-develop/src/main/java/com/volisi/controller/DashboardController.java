package com.volisi.controller;

import com.volisi.dto.PageBaseResponse;
import com.volisi.dto.request.GlobarSearchRequest;
import com.volisi.dto.response.QuizLibraryResponseRecord;
import com.volisi.enums.ResultCode;
import com.volisi.handler.DashboardHandler;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dashboard")
public class DashboardController {
  private final DashboardHandler dashboardHandler;

  private final MessageSource messageSource;

  public DashboardController(DashboardHandler dashboardHandler, MessageSource messageSource) {
    this.dashboardHandler = dashboardHandler;
    this.messageSource = messageSource;
  }

  @PostMapping("/filter")
  public PageBaseResponse<QuizLibraryResponseRecord> getQuizBySearchNameWithFilter(
      @Valid @RequestBody GlobarSearchRequest request) {
    Page<QuizLibraryResponseRecord> response = dashboardHandler.filter(request);
    return new PageBaseResponse<QuizLibraryResponseRecord>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "quiz.data.success", new Object[] {}, LocaleContextHolder.getLocale()),
        request.page(),
        request.pageSize(),
        response.getTotalElements(),
        response.getContent());
  }
}
