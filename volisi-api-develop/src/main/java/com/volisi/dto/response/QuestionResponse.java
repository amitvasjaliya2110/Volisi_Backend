package com.volisi.dto.response;

import com.volisi.model.AnswerOptionDto;
import com.volisi.model.QuestionTypeDto;
import java.util.List;

public record QuestionResponse(
    Long id,
    String question,
    int timeLimit,
    int points,
    QuestionTypeDto questionType,
    AnswerOptionDto answerOption,
    Long quizId,
    List<QuestionAnswerResponse> questionAnswers) {}
