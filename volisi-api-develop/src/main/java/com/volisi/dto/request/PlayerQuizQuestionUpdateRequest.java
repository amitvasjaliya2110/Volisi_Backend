package com.volisi.dto.request;

import com.volisi.model.AnswerOptionDto;
import com.volisi.model.PlayerQuizDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PlayerQuizQuestionUpdateRequest(
    Long id,
    @NotBlank String question,
    @NotNull AnswerOptionDto type,
    @NotNull PlayerQuizDto playerQuiz,
    boolean correct,
    int points,
    int timeTaken,
    @NotEmpty @Valid List<PlayerQuizAnswersUpdateRequest> playerQuizAnswers) {}
