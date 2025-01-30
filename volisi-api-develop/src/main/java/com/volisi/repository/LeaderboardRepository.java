package com.volisi.repository;

import com.volisi.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
  boolean existsByPlayerId(Long id);

  boolean existsByPlayerQuizId(Long id);
}
