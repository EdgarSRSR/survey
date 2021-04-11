package com.example.survey.Question;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.survey.Survey.Survey;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
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

  private final QuestionRepository questionRepository;
  private final QuestionModelAssembler assembler;

  QuestionController(QuestionRepository questionRepository, QuestionModelAssembler assembler) {

    this.questionRepository = questionRepository;
    this.assembler = assembler;
  }

  @GetMapping("/question")
  CollectionModel<EntityModel<Question>> all() {

    List<EntityModel<Question>> questions = questionRepository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(questions, //
        linkTo(methodOn(QuestionController.class).all()).withSelfRel());
  }

  @GetMapping("/questions/{id}")
  EntityModel<Question> one(@PathVariable Long id) {

    Question question = questionRepository.findById(id) //
        .orElseThrow(() -> new QuestionNotFoundException(id));

    return assembler.toModel(question);
  }

  @PostMapping("/questions")
  ResponseEntity<?> newQuestion(@RequestBody Question newQuestion) {

    EntityModel<Question> entityModel = assembler.toModel(questionRepository.save(newQuestion));
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @PutMapping("/questions/{id}")
  ResponseEntity<?> replaceQuestion(@RequestBody Question newQuestion, @PathVariable Long id) {

    Question updatedQuestion = questionRepository.findById(id) //
        .map(question -> {
          question.setLink(newQuestion.getLink());
          question.setText(newQuestion.getText());
          question.setDisplayOrder(newQuestion.getDisplayOrder());

          return questionRepository.save(question);
        }) //
        .orElseGet(() -> {
          newQuestion.setId(id);
          return questionRepository.save(newQuestion);
        });

    EntityModel<Question> entityModel = assembler.toModel(updatedQuestion);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  @DeleteMapping("/questions/{id}")
  ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
    questionRepository.deleteById(id);
    return ResponseEntity.noContent().build();

  }
}


