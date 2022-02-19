package org.hatulmadan.site.server.application.data.repositories;

import org.hatulmadan.site.server.application.data.entities.survey.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface SurveyDAO extends CrudRepository<Quiz, Long> {
}
