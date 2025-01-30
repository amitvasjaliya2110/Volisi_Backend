package com.volisi.dto.response;

import com.volisi.model.PlayerDto;
import com.volisi.model.PlayerQuizDto;

public record LeaderboardResponse(
    Long id, PlayerQuizDto playerQuiz, PlayerDto player, int points) {}
