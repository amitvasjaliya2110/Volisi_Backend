package com.volisi.dto.response;

import java.util.Date;

public record LoginResponse(Long userId, String username, String token, Date tokenExpiryTime) {}
