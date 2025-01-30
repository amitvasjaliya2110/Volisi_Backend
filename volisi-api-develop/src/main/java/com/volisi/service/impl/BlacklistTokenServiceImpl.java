package com.volisi.service.impl;

import com.volisi.entity.BlacklistedToken;
import com.volisi.repository.BlacklistedTokenRepository;
import com.volisi.service.BlacklistTokenService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BlacklistTokenServiceImpl implements BlacklistTokenService {

  private final BlacklistedTokenRepository blacklistedTokenRepository;

  public BlacklistTokenServiceImpl(BlacklistedTokenRepository blacklistedTokenRepository) {
    this.blacklistedTokenRepository = blacklistedTokenRepository;
  }

  public void blacklistToken(String token) {
    BlacklistedToken blacklistedToken = new BlacklistedToken();
    blacklistedToken.setToken(token.substring(7));
    blacklistedToken.setExpiryAt(LocalDateTime.now());
    blacklistedTokenRepository.save(blacklistedToken);
  }

  @Override
  public boolean isTokenBlacklisted(String token) {
    return blacklistedTokenRepository.existsByToken(token);
  }

  @Override
  public void deleteExpiredTokens() {
    List<BlacklistedToken> expiredTokens =
        blacklistedTokenRepository.findByExpiryAtBefore(LocalDateTime.now().minusMinutes(10));
    blacklistedTokenRepository.deleteAll(expiredTokens);
  }
}
