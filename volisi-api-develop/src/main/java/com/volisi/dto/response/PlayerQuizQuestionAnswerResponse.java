package com.volisi.dto.response;

import com.volisi.model.AnswerOptionDto;
import com.volisi.model.QuestionTypeDto;
import java.util.List;

public record PlayerQuizQuestionAnswerResponse(
    Long id,
    String question,
    QuestionTypeDto questionType,
    AnswerOptionDto type,
    List<PlayerQuizAnswerResponse> playerQuizAnswers) {}
