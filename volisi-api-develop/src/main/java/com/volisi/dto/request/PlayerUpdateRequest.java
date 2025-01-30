package com.volisi.dto.request;

import com.volisi.model.PlayerQuizDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlayerUpdateRequest(
    @NotNull @Min(value = 1, message = "{min.id.length}") Long id,
    @NotBlank @Size(max = 30) String name,
    @NotNull PlayerQuizDto playerQuiz) {}
