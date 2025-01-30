package com.volisi.job;

import com.volisi.service.BlacklistTokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenCleanupJob {

  private final BlacklistTokenService blacklistTokenService;

  public TokenCleanupJob(BlacklistTokenService blacklistTokenService) {
    this.blacklistTokenService = blacklistTokenService;
  }

  @Scheduled(cron = "0 0 12 * * ?")
  public void deleteExpiredTokens() {
    blacklistTokenService.deleteExpiredTokens();
  }
}
