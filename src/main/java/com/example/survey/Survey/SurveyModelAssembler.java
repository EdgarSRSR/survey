package com.example.survey.Survey;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class SurveyModelAssembler implements RepresentationModelAssembler<Survey, EntityModel<Survey>> {

  @Override
  public EntityModel<Survey> toModel(Survey survey) {

    return EntityModel.of(survey, //
        linkTo(methodOn(SurveyController.class).one(survey.getId())).withSelfRel(),
        linkTo(methodOn(SurveyController.class).all()).withRel("survey"));
  }
}