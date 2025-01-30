package com.volisi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PointconfigRequest(@NotBlank String name, @NotNull String description) {}
