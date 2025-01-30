package com.volisi.dto.request;

import com.volisi.enums.QuizStatus;
import com.volisi.model.CollectionDto;
import com.volisi.model.UserDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record QuizRequestRecord(
    @NotNull
        @NotEmpty
        @Size(max = 30, message = "{max.name.length}")
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{name.pattern.error}")
        @Column(unique = true)
        String name,
    @Size(max = 300, message = "{max.description.length}") String description,
    String coverImage,
    QuizStatus status,
    boolean is_deleted,
    @NotNull UserDto user,
    CollectionDto collectionId) {}
