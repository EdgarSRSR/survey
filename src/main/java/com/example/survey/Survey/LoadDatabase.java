package com.example.survey.Survey;

import java.sql.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log  = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(SurveyRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Survey("Test survey",  new Date(2017,10,22),  new Date(2017,10,23), true )));
    };
  }

}
