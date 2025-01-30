package com.volisi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
    @NotEmpty
        @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "{username.pattern.validation.error}")
        String username,
    @Email(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "{email.pattern.validation.error}")
        String email,
    @NotNull
        @Size(min = 8)
        @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,}$",
            message = "{password.pattern.validation.error}")
        String password,
    boolean active) {}
