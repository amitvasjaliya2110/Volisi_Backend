package com.volisi.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlayerQuizAnswerResponse(
    @NotBlank String questionOption,
    Long selectedOption,
    boolean correctOption,
    @NotNull int optionSequence) {}
