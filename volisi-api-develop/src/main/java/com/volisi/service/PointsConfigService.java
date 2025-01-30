package com.volisi.service;

import com.volisi.entity.PointsConfig;
import java.util.List;

public interface PointsConfigService {
  PointsConfig create(PointsConfig pointsConfig);

  List<PointsConfig> getAll();

  PointsConfig getById(Long id);

  PointsConfig update(PointsConfig pointsConfig);

  void deleteById(Long id);
}
