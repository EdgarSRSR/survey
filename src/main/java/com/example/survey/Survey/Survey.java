package com.example.survey.Survey;

import com.example.survey.Question.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Date;
import java.util.List;
import javax.persistence.Id;

import java.util.Objects;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
@Table(name = "survey")
public class Survey {

  private @Id @GeneratedValue
  Long id;

  private String nameSurvey;

  private Date startDate;

  private Date endDate;

  private Boolean activity;

  @JsonIgnore
  @ManyToMany(mappedBy = "survey")
  private List<Question> questions;

  Survey(){}

  public Survey(String nameSurvey, Date startDate, Date endDate, Boolean activity) {
    this.nameSurvey = nameSurvey;
    this.startDate = startDate;
    this.endDate = endDate;
    this.activity = activity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNameSurvey() {
    return nameSurvey;
  }

  public void setNameSurvey(String nameSurvey) {
    this.nameSurvey = nameSurvey;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Boolean getActivity() {
    return activity;
  }

  public void setActivity(Boolean activity) {
    this.activity = activity;
  }

  @Override
  public String toString() {
    return "Survey{" +
        "id=" + id +
        ", nameSurvey='" + nameSurvey + '\'' +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", activity=" + activity +
        '}';
  }
}
