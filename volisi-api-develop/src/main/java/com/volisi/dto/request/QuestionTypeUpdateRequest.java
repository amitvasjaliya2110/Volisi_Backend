package com.volisi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionTypeUpdateRequest(
    @NotNull @Min(value = 1, message = "{min.id.length}") Long id, @NotBlank String name) {}
