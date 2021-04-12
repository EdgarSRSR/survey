package com.example.survey.Question;

import com.example.survey.Survey.Survey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

// Вопрос опроса
@Entity
@Table(name = "question")
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  //Ссылка на опрос
  private String link;
  //Текст вопроса
  private String text;
  // Порядок отображения
  private int displayOrder;

  Question(){}
  public Question(String link, String text, int displayOrder) {
    this.link = link;
    this.text = text;
    this.displayOrder = displayOrder;
  }



  @Override
  public String toString() {
    return "Question{" +
        "id=" + id +
        ", link='" + link + '\'' +
        ", text='" + text + '\'' +
        ", displayOrder=" + displayOrder +
        '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(int displayOrder) {
    this.displayOrder = displayOrder;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Question question = (Question) o;
    return displayOrder == question.displayOrder &&
        Objects.equals(id, question.id) &&
        Objects.equals(link, question.link) &&
        Objects.equals(text, question.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, link, text, displayOrder);
  }
}
