package com.volisi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Audit {
  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdDate;

  @UpdateTimestamp private LocalDateTime updatedDate;

  private String createdBy;

  private String updatedBy;
}
