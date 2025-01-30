package com.volisi.dto.request;

import com.volisi.model.PlayerQuizDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlayerRequest(
    @NotBlank @Size(max = 30) String name, @NotNull PlayerQuizDto playerQuiz) {}
