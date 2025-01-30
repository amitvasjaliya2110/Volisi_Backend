package com.volisi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "player_quiz_question")
public class PlayerQuizQuestion extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String question;

  @ManyToOne private QuestionType questionType;

  @ManyToOne private AnswerOption type;

  @ManyToOne private PlayerQuiz playerQuiz;

  @Column(name = "is_correct", columnDefinition = "boolean default false")
  private boolean correct;

  @ColumnDefault(value = "false")
  private boolean noAnswer;

  @ColumnDefault(value = "0")
  private int points;

  private int timeTaken;

  @ManyToOne private Player player;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinColumn(name = "question_id", referencedColumnName = "id")
  private List<PlayerQuizAnswers> playerQuizAnswers;
}
