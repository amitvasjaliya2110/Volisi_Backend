package com.volisi.mapper;

import com.volisi.dto.request.PlayerQuizRequestRecord;
import com.volisi.dto.request.PlayerQuizUpdateRequest;
import com.volisi.dto.response.PlayerQuizResponseRecord;
import com.volisi.dto.response.QuizLibraryResponseRecord;
import com.volisi.entity.PlayerQuiz;
import com.volisi.projection.PlayerQuizQuestionTypeCount;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlayerQuizMapper {
  PlayerQuizResponseRecord toResponse(PlayerQuiz playerQuiz);

  PlayerQuiz toEntity(PlayerQuizRequestRecord playerQuizRequest);

  List<PlayerQuizResponseRecord> toList(List<PlayerQuiz> playerQuiz);

  List<QuizLibraryResponseRecord> toLibraryList(List<PlayerQuiz> quiz);

  PlayerQuiz toEntity(PlayerQuizUpdateRequest updateRequest);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "performancePercentage", ignore = true)
  @Mapping(target = "code", ignore = true)
  PlayerQuiz toEntity(PlayerQuiz requestEntity, @MappingTarget PlayerQuiz existingEntity);

  @Mapping(target = "totalQuestions", source = "questionCount")
  @Mapping(target = "playerQuizQuestionTypeCounts", source = "questionTypeCounts")
  PlayerQuizResponseRecord toPlayerQuizDetails(
      PlayerQuiz playerQuiz,
      Long questionCount,
      List<PlayerQuizQuestionTypeCount> questionTypeCounts);
}
