package com.volisi.service;

import com.volisi.entity.User;
import java.util.List;

public interface UserService {
  User save(User user);

  User getById(Long id);

  List<User> getAll();

  User update(User user);

  User patchUpdate(User user);

  void delete(Long id);

  User findByUsername(String userName);
}
