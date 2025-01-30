package com.volisi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record QuizPaginationRequest(
    @Min(0) int pageNo, @Min(1) int pageSize, @NotNull Long collectionId, @NotNull Long userId) {}
