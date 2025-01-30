package com.volisi.mapper;

import com.volisi.dto.request.PointconfigRequest;
import com.volisi.dto.request.PointsConfigUpdateRequest;
import com.volisi.dto.response.PointconfigResponce;
import com.volisi.entity.PointsConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PointconfigMapper {
  PointsConfig toEntity(PointconfigRequest pointconfigRequest);

  PointconfigResponce toResponse(PointsConfig pointsConfig);

  List<PointconfigResponce> toList(List<PointsConfig> pointsConfigList);

  PointsConfig toEntity(PointsConfigUpdateRequest pointsConfigUpdateRequest);

  @Mapping(target = "id", ignore = true)
  PointsConfig toDbEntity(
      PointsConfig pointsConfig, @MappingTarget PointsConfig existingPointsConfig);
}
