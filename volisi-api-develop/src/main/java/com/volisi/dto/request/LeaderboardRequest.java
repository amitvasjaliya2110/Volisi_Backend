package com.volisi.dto.request;

import com.volisi.model.PlayerDto;
import com.volisi.model.PlayerQuizDto;
import jakarta.validation.constraints.NotNull;

public record LeaderboardRequest(
    @NotNull PlayerQuizDto playerQuiz, @NotNull PlayerDto player, int points) {}
