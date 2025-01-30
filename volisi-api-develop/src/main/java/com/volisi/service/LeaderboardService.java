package com.volisi.service;

import com.volisi.entity.Leaderboard;
import java.util.List;

public interface LeaderboardService {
  Leaderboard create(Leaderboard leaderboard);

  List<Leaderboard> getAll();

  Leaderboard getById(Long id);

  Leaderboard update(Leaderboard leaderboard);

  void delete(Long id);

  boolean existsByPlayerId(Long id);

  boolean existsByPlayerQuizId(Long id);
}
