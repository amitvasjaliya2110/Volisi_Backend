package com.volisi.dto.request;

import com.volisi.constant.Constant;
import com.volisi.model.CollectionDto;
import com.volisi.model.UserDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record QuizUpdateRequest(
    @NotNull @Min(value = 1, message = "{min.id.length}") Long id,
    @NotNull
        @NotEmpty
        @Size(max = 30, message = "{max.name.length}")
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{name.pattern.error}")
        @Column(unique = true)
        String name,
    @Size(max = 300, message = "{max.description.length}") String description,
    String coverImage,
    @Pattern(
            regexp = "(?i)" + Constant.QUIZ_STATUS_CREATED + "|" + Constant.QUIZ_STATUS_DRAFT,
            message = "{invalid.status.error}")
        String status,
    @NotNull UserDto user,
    @NotNull CollectionDto collection) {}
