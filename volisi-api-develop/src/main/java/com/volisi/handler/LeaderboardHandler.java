package com.volisi.handler;

import com.volisi.dto.request.LeaderboardRequest;
import com.volisi.dto.request.LeaderboardUpdateRequest;
import com.volisi.dto.response.LeaderboardResponse;
import com.volisi.entity.Leaderboard;
import com.volisi.mapper.LeaderboardMapper;
import com.volisi.service.LeaderboardService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LeaderboardHandler {
  private final LeaderboardService leaderboardService;
  private final LeaderboardMapper leaderboardMapper;

  public LeaderboardHandler(
      LeaderboardService leaderboardService, LeaderboardMapper leaderboardMapper) {
    this.leaderboardService = leaderboardService;
    this.leaderboardMapper = leaderboardMapper;
  }

  public LeaderboardResponse create(LeaderboardRequest leaderboardRequest) {
    Leaderboard leaderboard = leaderboardMapper.toEntity(leaderboardRequest);
    return leaderboardMapper.toResponse(leaderboardService.create(leaderboard));
  }

  public List<LeaderboardResponse> getAll() {
    return leaderboardMapper.toList(leaderboardService.getAll());
  }

  public LeaderboardResponse getById(Long id) {
    return leaderboardMapper.toResponse(leaderboardService.getById(id));
  }

  public LeaderboardResponse update(LeaderboardUpdateRequest leaderboardUpdateRequest) {
    Leaderboard leaderboard = leaderboardMapper.toEntity(leaderboardUpdateRequest);
    return leaderboardMapper.toResponse(leaderboardService.update(leaderboard));
  }

  public void delete(Long id) {
    leaderboardService.delete(id);
  }
}
