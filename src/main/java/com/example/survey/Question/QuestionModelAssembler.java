package com.example.survey.Question;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.survey.Survey.Survey;
import com.example.survey.Survey.SurveyController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class QuestionModelAssembler implements RepresentationModelAssembler<Question, EntityModel<Question>> {

  @Override
  public EntityModel<Question> toModel(Question question) {

    return EntityModel.of(question, //
        linkTo(methodOn(QuestionController.class).one(question.getId())).withSelfRel(),
        linkTo(methodOn(QuestionController.class).all()).withRel("question"));
  }

}
