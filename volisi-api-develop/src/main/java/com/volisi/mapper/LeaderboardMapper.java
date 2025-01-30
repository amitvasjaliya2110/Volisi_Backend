package com.volisi.mapper;

import com.volisi.dto.request.LeaderboardRequest;
import com.volisi.dto.request.LeaderboardUpdateRequest;
import com.volisi.dto.response.LeaderboardResponse;
import com.volisi.entity.Leaderboard;
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
public interface LeaderboardMapper {

  Leaderboard toEntity(LeaderboardRequest leaderboardRequest);

  LeaderboardResponse toResponse(Leaderboard leaderboard);

  List<LeaderboardResponse> toList(List<Leaderboard> leaderboardList);

  Leaderboard toEntity(LeaderboardUpdateRequest leaderboardUpdateRequest);

  @Mapping(target = "id", ignore = true)
  Leaderboard toDbEntity(Leaderboard leaderboard, @MappingTarget Leaderboard existingLeaderboard);
}
