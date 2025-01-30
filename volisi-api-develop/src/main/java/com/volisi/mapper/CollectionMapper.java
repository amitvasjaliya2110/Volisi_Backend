package com.volisi.mapper;

import com.volisi.dto.request.CollectionRequest;
import com.volisi.dto.request.CollectionUpdateRequest;
import com.volisi.dto.response.CollectionResponse;
import com.volisi.entity.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CollectionMapper {

  Collection toEntity(CollectionRequest collectionRequest);

  Collection toEntity(CollectionUpdateRequest collectionUpdateRequest);

  @Mapping(target = "id", ignore = true)
  Collection toDbEntity(Collection collection, @MappingTarget Collection existingCollection);

  CollectionResponse toResponse(Collection collection);

  List<CollectionResponse> toList(List<Collection> questionList);
}
