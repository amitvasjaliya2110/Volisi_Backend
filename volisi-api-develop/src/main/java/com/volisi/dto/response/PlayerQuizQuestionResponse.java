package com.volisi.dto.response;

import com.volisi.model.AnswerOptionDto;
import com.volisi.model.PlayerQuizDto;
import java.util.List;

public record PlayerQuizQuestionResponse(
    String question,
    AnswerOptionDto type,
    PlayerQuizDto playerQuiz,
    boolean correct,
    boolean noAnswer,
    int points,
    int timeTaken,
    List<PlayerQuizAnswerResponse> playerQuizAnswers) {}
