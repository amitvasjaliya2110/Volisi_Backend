package com.volisi.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PlayerQuizDto {
  private Long id;
  private String name;
  private QuizDto quiz;
  private int players;
  private long time;
  private int code;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private double performancePercentage;
}
