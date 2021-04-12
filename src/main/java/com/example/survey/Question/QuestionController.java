package com.example.survey.Question;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.example.survey.Survey.Survey;
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
public class QuestionController {

  private final QuestionRepository repository;
  private final QuestionModelAssembler assembler;

  QuestionController(QuestionRepository repository, QuestionModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  //  Получить все вопросы
  @GetMapping("/questions")
  CollectionModel<EntityModel<Question>> all() {

    List<EntityModel<Question>> questions = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(questions, linkTo(methodOn(QuestionController.class).all()).withSelfRel());
  }


  // Создание вопроса
  @PostMapping("/createquestions")
  ResponseEntity<?> newQuestion( @RequestBody Question newQuestion) {

    EntityModel<Question> entityModel = assembler.toModel(repository.save(newQuestion));
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }
  // один элемент

  @GetMapping("/questions/{id}")
  EntityModel <Question> one(@PathVariable Long id) {

    Question question = repository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    return assembler.toModel(question);
  }

  //Редактирование вопроса
  @PutMapping("/questions/{id}")
  ResponseEntity<?>  replaceQuestion(@RequestBody Question newQuestion, @PathVariable Long id) {

    Question updatedQuestion = repository.findById(id) //
        .map(question -> {
          question.setLink(newQuestion.getLink());
          question.setText(newQuestion.getText());
          question.setDisplayOrder(newQuestion.getDisplayOrder());
          return repository.save(question);
        })
        .orElseGet(() -> {
          newQuestion.setId(id);
          return repository.save(newQuestion);
        });
    EntityModel<Question> entityModel = assembler.toModel(updatedQuestion);
              return ResponseEntity //
                  .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                  .body(entityModel);
  }

  // Удаление вопроса
  @DeleteMapping("/questions/{id}")
  ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

}
