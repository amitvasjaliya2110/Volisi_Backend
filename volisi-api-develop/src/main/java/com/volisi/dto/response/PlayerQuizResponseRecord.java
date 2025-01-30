package com.volisi.dto.response;

import com.volisi.model.QuizDto;
import com.volisi.projection.PlayerQuizQuestionTypeCount;
import java.time.LocalDateTime;
import java.util.List;

public record PlayerQuizResponseRecord(
    String name,
    QuizDto quiz,
    int players,
    double time,
    double performancePercentage,
    int code,
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    Long totalQuestions,
    List<PlayerQuizQuestionTypeCount> playerQuizQuestionTypeCounts) {}
