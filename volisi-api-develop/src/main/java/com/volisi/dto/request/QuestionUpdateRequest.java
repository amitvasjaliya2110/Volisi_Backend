package com.volisi.dto.request;

import com.volisi.model.AnswerOptionDto;
import com.volisi.model.QuestionTypeDto;
import com.volisi.model.QuizDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record QuestionUpdateRequest(
    @NotNull @Min(value = 1, message = "{min.id.length}") Long id,
    @NotBlank @Size(max = 3000) String question,
    int timeLimit,
    QuestionTypeDto questionType,
    AnswerOptionDto answerOption,
    @NotNull QuizDto quiz,
    @Valid List<QuestionAnswerUpdateRequest> questionAnswers) {}
