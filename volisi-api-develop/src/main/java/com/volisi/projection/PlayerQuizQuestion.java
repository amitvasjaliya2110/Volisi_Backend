package com.volisi.projection;

public interface PlayerQuizQuestion {
  Long getId();

  String getName();

  Integer getFinalScore();

  Integer getRanking();

  Integer getTotalCorrectAnswers();

  Integer getTotalUnansweredQuestions();

  Integer getTotalTime();
}
