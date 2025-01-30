package com.volisi.mapper;

import com.volisi.dto.request.QuestionAnswerRequest;
import com.volisi.dto.response.QuestionAnswerResponse;
import com.volisi.entity.QuestionAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionAnswerMapper {

  QuestionAnswer toEntity(QuestionAnswerRequest questionAnswerRequest);

  QuestionAnswerResponse toResponse(QuestionAnswer questionAnswer);
}
