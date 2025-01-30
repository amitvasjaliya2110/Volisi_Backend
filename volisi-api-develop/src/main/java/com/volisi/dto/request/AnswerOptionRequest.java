package com.volisi.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record AnswerOptionRequest(@NotEmpty String name) {}
