package com.volisi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
    @NotNull @Min(value = 1, message = "{min.id.length}") Long id,
    @NotEmpty
        @Size(min = 5, max = 15)
        @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9_]{5,15}$",
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
        String password) {}
