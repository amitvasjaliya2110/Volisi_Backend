package com.volisi.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AnswerOptionUpdateRequest(@NotNull Long id, @NotEmpty String name) {}
