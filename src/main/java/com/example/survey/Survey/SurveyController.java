package com.example.survey.Survey;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {

  private final SurveyRepository repository;

  private final SurveyModelAssembler assembler;

  SurveyController(SurveyRepository repository, SurveyModelAssembler assembler) {

    this.repository = repository;
    this.assembler = assembler;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  //  Получить все опросы
  @GetMapping("/surveys")
  CollectionModel<EntityModel<Survey>> all() {

    List<EntityModel<Survey>> surveys = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(surveys, linkTo(methodOn(SurveyController.class).all()).withSelfRel());
  }

  // Создание опроса
  @PostMapping("/surveys")
  ResponseEntity<?> newSurvey( Survey newSurvey) {

    EntityModel<Survey> entityModel = assembler.toModel(repository.save(newSurvey));
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  // один элемент

  @GetMapping("/surveys/{id}")
  EntityModel <Survey> one(@PathVariable Long id) {

    Survey survey = repository.findById(id).orElseThrow(() -> new SurveyNotFoundException(id));
    return assembler.toModel(survey);
  }

  //Редактирование опроса
  @PutMapping("/surveys/{id}")
  ResponseEntity<?> replaceSurvey( Survey newSurvey, @PathVariable Long id) {

    Survey updatedSurvey = repository.findById(id) //
        .map(survey -> {
          survey.setNameSurvey(newSurvey.getNameSurvey());
          survey.setStartDate(newSurvey.getStartDate());
          survey.setEndDate(newSurvey.getEndDate());
          survey.setActivity(newSurvey.getActivity());
          return repository.save(survey);
        }) //
        .orElseGet(() -> {
          newSurvey.setId(id);
          return repository.save(newSurvey);
        });

    EntityModel<Survey> entityModel = assembler.toModel(updatedSurvey);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  // Удаление опроса
  @DeleteMapping("/surveys/{id}")
  ResponseEntity<?> deleteSurvey(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();

  }

}
