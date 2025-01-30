package com.volisi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerQuiz extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToOne private Quiz quiz;

  private Integer players = 0;

  @OneToMany(mappedBy = "playerQuiz")
  private List<PlayerQuizQuestion> questions;

  private long time;
  private int code;
  private String status;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;

  @Column(name = "performance_percentage")
  private double performancePercentage = 0.0;

  @OneToOne private User user;
}
