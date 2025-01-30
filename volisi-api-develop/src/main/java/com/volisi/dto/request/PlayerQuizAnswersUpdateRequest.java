package com.volisi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlayerQuizAnswersUpdateRequest(
    Long id, @NotBlank String questionOption, Long selectedOption, @NotNull int optionSequence) {}
