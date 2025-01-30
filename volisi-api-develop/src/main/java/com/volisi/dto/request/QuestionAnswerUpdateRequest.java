package com.volisi.dto.request;

import jakarta.validation.constraints.Size;

public record QuestionAnswerUpdateRequest(
    Long id,
    @Size(max = 3000, message = "Option length cannot exceed {max} characters")
        String questionOption,
    boolean correct) {}
