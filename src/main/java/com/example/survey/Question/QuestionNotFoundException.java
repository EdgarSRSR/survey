package com.example.survey.Question;

public class QuestionNotFoundException extends  RuntimeException{
  QuestionNotFoundException(Long id) {
    super("Could not find Question " + id);
  }

}
