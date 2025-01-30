package com.volisi.repository;

import com.volisi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String name);

  @Modifying
  @Transactional
  @Query("UPDATE User u SET u.verified = :verified WHERE u.id = :userId")
  void updateVerified(@Param("verified") boolean verified, @Param("userId") Long userId);
}
