package com.volisi.dto.response;

import com.volisi.model.CollectionDto;
import com.volisi.model.UserDto;

public record QuizResponseRecord(
    Long id,
    String name,
    String description,
    String coverImage,
    String status,
    UserDto user,
    CollectionDto collection) {}
