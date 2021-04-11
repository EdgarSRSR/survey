package com.example.survey.Question;

class QuestionNotFoundException extends  RuntimeException{
  QuestionNotFoundException(Long id) {
    super("Could not find Question " + id);
  }

}