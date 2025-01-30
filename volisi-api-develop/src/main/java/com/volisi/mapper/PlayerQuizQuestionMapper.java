package com.volisi.mapper;

import com.volisi.dto.request.PlayerQuizQuestionRequest;
import com.volisi.dto.request.PlayerQuizQuestionUpdateRequest;
import com.volisi.dto.response.PlayerQuizQuestionAnswerResponse;
import com.volisi.dto.response.PlayerQuizQuestionResponse;
import com.volisi.entity.PlayerQuizQuestion;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    uses = PlayerQuizAnswerMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlayerQuizQuestionMapper {
  @Mapping(target = "playerQuizAnswers", source = "playerQuizAnswers")
  PlayerQuizQuestion toEntity(PlayerQuizQuestionRequest playerQuizQuestionRequest);

  PlayerQuizQuestionResponse toResponse(PlayerQuizQuestion playerQuizQuestion);

  List<PlayerQuizQuestionResponse> toList(List<PlayerQuizQuestion> playerQuizQuestion);

  List<PlayerQuizQuestionAnswerResponse> toAnswerList(List<PlayerQuizQuestion> playerQuizQuestion);

  PlayerQuizQuestion toUpdateEntity(PlayerQuizQuestionUpdateRequest playerQuizQuestionRequest);

  @Mapping(target = "id", ignore = true)
  PlayerQuizQuestion toDbEntity(
      PlayerQuizQuestion playerQuizQuestion,
      @MappingTarget PlayerQuizQuestion existingPlayerQuizQuestion);
}
