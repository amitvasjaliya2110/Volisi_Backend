package com.volisi.repository;

import com.volisi.entity.PointsConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsConfigRepository extends JpaRepository<PointsConfig, Long> {
  PointsConfig findByName(String name);
}
