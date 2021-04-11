package com.example.survey.Survey;

class SurveyNotFoundException extends  RuntimeException{
  SurveyNotFoundException(Long id) {
    super("Could not find Survey " + id);
  }

}
