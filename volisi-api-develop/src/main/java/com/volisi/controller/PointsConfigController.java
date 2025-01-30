package com.volisi.controller;

import com.volisi.constant.Constant;
import com.volisi.dto.BaseResponse;
import com.volisi.dto.request.PointconfigRequest;
import com.volisi.dto.request.PointsConfigUpdateRequest;
import com.volisi.dto.response.PointconfigResponce;
import com.volisi.enums.ResultCode;
import com.volisi.handler.PointconfigHandler;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/points")
public class PointsConfigController {
  private final PointconfigHandler pointconfigHandler;
  private final MessageSource messageSource;

  public PointsConfigController(
      PointconfigHandler pointconfigHandler, MessageSource messageSource) {
    this.pointconfigHandler = pointconfigHandler;
    this.messageSource = messageSource;
  }

  @PostMapping
  public BaseResponse<PointconfigResponce> create(
      @Valid @RequestBody PointconfigRequest pointconfigRequest) {
    PointconfigResponce response = pointconfigHandler.create(pointconfigRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "saved.data.success", new Object[] {Constant.POINTS_CONFIG}, Locale.getDefault()),
        response);
  }

  @GetMapping
  public BaseResponse<List<PointconfigResponce>> getAll() {
    List<PointconfigResponce> response = pointconfigHandler.getAll();
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.POINTS_CONFIG}, Locale.getDefault()),
        response);
  }

  @GetMapping("/{id}")
  public BaseResponse<PointconfigResponce> getById(@PathVariable Long id) {
    PointconfigResponce response = pointconfigHandler.getById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.POINTS_CONFIG}, Locale.getDefault()),
        response);
  }

  @PutMapping
  public BaseResponse<PointconfigResponce> update(
      @RequestBody @Valid PointsConfigUpdateRequest pointsConfigUpdateRequest) {
    PointconfigResponce response = pointconfigHandler.update(pointsConfigUpdateRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "updated.data.success", new Object[] {Constant.POINTS_CONFIG}, Locale.getDefault()),
        response);
  }

  @DeleteMapping("/{id}")
  public BaseResponse<String> deleteById(@PathVariable Long id) {
    pointconfigHandler.deleteById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "delete.data.success", new Object[] {Constant.POINTS_CONFIG}, Locale.getDefault()));
  }
}
