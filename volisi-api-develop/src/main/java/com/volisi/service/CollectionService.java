package com.volisi.service;

import com.volisi.entity.Collection;
import java.util.List;

public interface CollectionService {
  Collection create(Collection collection);

  List<Collection> getAll();

  Collection getById(Long id);

  Collection update(Collection collection);

  void delete(Long id);

  List<Collection> findByUserId(Long userId);
}
