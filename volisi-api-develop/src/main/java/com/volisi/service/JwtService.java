package com.volisi.service;

import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractUsername(String token);

  Date extractExpiration(String token);

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  Boolean isTokenExpired(String token);

  boolean validateToken(String token, UserDetails userDetails);

  String generateToken(UserDetails userDetails);
}
