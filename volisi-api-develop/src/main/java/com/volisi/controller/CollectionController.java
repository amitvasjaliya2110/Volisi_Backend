package com.volisi.controller;

import com.volisi.constant.Constant;
import com.volisi.dto.BaseResponse;
import com.volisi.dto.request.CollectionRequest;
import com.volisi.dto.request.CollectionUpdateRequest;
import com.volisi.dto.response.CollectionResponse;
import com.volisi.enums.ResultCode;
import com.volisi.handler.CollectionHandler;
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
@RequestMapping("/api/collection")
public class CollectionController {
  private final CollectionHandler collectionHandler;
  private final MessageSource messageSource;

  public CollectionController(CollectionHandler collectionHandler, MessageSource messageSource) {
    this.collectionHandler = collectionHandler;
    this.messageSource = messageSource;
  }

  @PostMapping
  public BaseResponse<CollectionResponse> create(
      @RequestBody @Valid CollectionRequest collectionRequest) {
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "saved.data.success", new Object[] {Constant.COLLECTION}, Locale.getDefault()),
        collectionHandler.create(collectionRequest));
  }

  @GetMapping
  public BaseResponse<List<CollectionResponse>> getAll() {
    List<CollectionResponse> response = collectionHandler.getAll();
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.COLLECTION}, Locale.getDefault()),
        response);
  }

  @GetMapping("/{id}")
  public BaseResponse<CollectionResponse> getById(@PathVariable Long id) {
    CollectionResponse response = collectionHandler.getById(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.COLLECTION}, Locale.getDefault()),
        response);
  }

  @PutMapping
  public BaseResponse<CollectionResponse> update(
      @RequestBody @Valid CollectionUpdateRequest collectionUpdateRequest) {
    CollectionResponse response = collectionHandler.update(collectionUpdateRequest);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "updated.data.success", new Object[] {Constant.PLAYER}, Locale.getDefault()),
        response);
  }

  @DeleteMapping("/{id}")
  public BaseResponse<String> delete(@PathVariable Long id) {
    collectionHandler.delete(id);
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "delete.data.success", new String[] {Constant.COLLECTION}, Locale.getDefault()),
        null);
  }

  @GetMapping("user/{userId}")
  public BaseResponse<List<CollectionResponse>> findByUser(@PathVariable Long userId) {
    return new BaseResponse<>(
        ResultCode.SUCCESS.getCode(),
        messageSource.getMessage(
            "found.data.success", new Object[] {Constant.COLLECTION}, Locale.getDefault()),
        collectionHandler.findByUserId(userId));
  }
}
