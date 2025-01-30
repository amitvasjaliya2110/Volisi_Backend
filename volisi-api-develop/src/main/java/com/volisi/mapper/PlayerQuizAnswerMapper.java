package com.volisi.mapper;

import com.volisi.dto.request.PlayerQuizAnswersRequest;
import com.volisi.dto.response.PlayerQuizAnswerResponse;
import com.volisi.entity.PlayerQuizAnswers;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlayerQuizAnswerMapper {
  PlayerQuizAnswers toEntity(PlayerQuizAnswersRequest playerQuizAnswersRequest);

  PlayerQuizAnswerResponse toResponse(PlayerQuizAnswers playerQuizAnswers);
}
