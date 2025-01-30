package com.volisi.dto.response;

import com.volisi.model.PlayerQuizDto;

public record PlayerResponse(
    Long id,
    String name,
    int ranking,
    int finalScore,
    boolean questionsAnswered,
    PlayerQuizDto playerQuiz) {}
