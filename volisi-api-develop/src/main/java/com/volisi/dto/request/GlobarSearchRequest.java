package com.volisi.dto.request;

import jakarta.validation.constraints.NotNull;

public record GlobarSearchRequest(
    int page, int pageSize, String name, @NotNull long userId, String filterType) {}
