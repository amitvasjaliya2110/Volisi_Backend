package com.volisi.dto.request;

import com.volisi.model.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CollectionRequest(@NotBlank String name, @NotNull UserDto user) {}
