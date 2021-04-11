package com.example.survey.Survey;


import java.util.List;
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

  SurveyController(SurveyRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/surveys")
  List<Survey> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/surveys")
  Survey newSurvey(@RequestBody Survey newSurvey) {
    return repository.save(newSurvey);
  }

  // Single item

  @GetMapping("/surveys/{id}")
  Survey one(@PathVariable Long id) {

    return repository.findById(id)
        .orElseThrow(() -> new SurveyNotFoundException(id));
  }

  @PutMapping("/surveys/{id}")
  Survey replaceSurvey(@RequestBody Survey newSurvey, @PathVariable Long id) {

    return repository.findById(id)
        .map(survey -> {
          survey.setNameSurvey(newSurvey.getNameSurvey());
          survey.setStartDate(newSurvey.getStartDate());
          survey.setEndDate(newSurvey.getEndDate());
          survey.setActivity(newSurvey.getActivity());
          return repository.save(survey);
        })
        .orElseGet(() -> {
          newSurvey.setId(id);
          return repository.save(newSurvey);
        });
  }

  @DeleteMapping("/surveys/{id}")
  void deleteSurvey(@PathVariable Long id) {
    repository.deleteById(id);
  }

}
