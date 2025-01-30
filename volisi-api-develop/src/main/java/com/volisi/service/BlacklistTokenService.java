package com.volisi.service;

public interface BlacklistTokenService {

  void blacklistToken(String token);

  boolean isTokenBlacklisted(String token);

  void deleteExpiredTokens();
}
