package com.volisi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String question;

  private int timeLimit;

  private int points;

  @ManyToOne private QuestionType questionType;
  @ManyToOne private AnswerOption answerOption;

  @ManyToOne private Quiz quiz;

  @Column(name = "is_deleted", columnDefinition = "boolean default false")
  private boolean deleted;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "question_id", referencedColumnName = "id")
  private List<QuestionAnswer> questionAnswers;
}
