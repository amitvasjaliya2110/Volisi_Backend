package com.volisi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlayerQuizAnswersRequest(
    @NotBlank String questionOption,
    Long selectedOption,
    boolean correctOption,
    @NotNull int optionSequence) {}
