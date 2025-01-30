package com.volisi.mapper;

import com.volisi.dto.request.PlayerRequest;
import com.volisi.dto.request.PlayerUpdateRequest;
import com.volisi.dto.response.PlayerResponse;
import com.volisi.entity.Player;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlayerMapper {

  Player toEntity(PlayerRequest playerRequest);

  Player toEntity(PlayerUpdateRequest playerUpdateRequest);

  @Mapping(target = "id", ignore = true)
  Player toDbEntity(Player player, @MappingTarget Player existingCollection);

  PlayerResponse toResponse(Player player);

  List<PlayerResponse> toList(List<Player> questionList);
}
