package com.volisi.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OtpRequest(Long id, @NotBlank String otp) {}
