package com.volisi.dto.request;

import jakarta.validation.constraints.NotNull;

public record PlayerPinRequest(@NotNull int code) {}
