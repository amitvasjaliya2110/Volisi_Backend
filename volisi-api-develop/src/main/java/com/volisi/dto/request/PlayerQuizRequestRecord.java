package com.volisi.dto.request;

import com.volisi.model.QuizDto;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record PlayerQuizRequestRecord(
    @NotNull
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{name.pattern.error}")
        @Size(max = 30)
        String name,
    @NotNull QuizDto quiz,
    @Min(value = 0) int players,
    @DecimalMin(value = "0") @DecimalMax(value = "100") double performancePercentage,
    @NotNull LocalDateTime startDateTime,
    @NotNull LocalDateTime endDateTime) {}
