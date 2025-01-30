package com.volisi.handler;

import com.volisi.dto.request.PointconfigRequest;
import com.volisi.dto.request.PointsConfigUpdateRequest;
import com.volisi.dto.response.PointconfigResponce;
import com.volisi.entity.PointsConfig;
import com.volisi.mapper.PointconfigMapper;
import com.volisi.service.PointsConfigService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PointconfigHandler {
  private final PointsConfigService pointsConfigService;
  private final PointconfigMapper pointconfigMapper;

  public PointconfigHandler(
      PointsConfigService pointsConfigService, PointconfigMapper pointconfigMapper) {
    this.pointsConfigService = pointsConfigService;
    this.pointconfigMapper = pointconfigMapper;
  }

  public PointconfigResponce create(PointconfigRequest pointconfigRequest) {
    PointsConfig pointsConfig = pointconfigMapper.toEntity(pointconfigRequest);
    return pointconfigMapper.toResponse(pointsConfigService.create(pointsConfig));
  }

  public List<PointconfigResponce> getAll() {
    return pointconfigMapper.toList(pointsConfigService.getAll());
  }

  public PointconfigResponce getById(Long id) {
    return pointconfigMapper.toResponse(pointsConfigService.getById(id));
  }

  public PointconfigResponce update(PointsConfigUpdateRequest pointsConfigUpdateRequest) {
    PointsConfig pointsConfig = pointconfigMapper.toEntity(pointsConfigUpdateRequest);
    return pointconfigMapper.toResponse(pointsConfigService.update(pointsConfig));
  }

  public void deleteById(Long id) {
    pointsConfigService.deleteById(id);
  }
}
