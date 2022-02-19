package org.hatulmadan.site.server.application.data.repositories;

import org.hatulmadan.site.server.application.data.entities.survey.QuizAnswer;
import org.hatulmadan.site.server.application.data.entities.survey.QuizQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswersDAO extends CrudRepository<QuizAnswer, Long> {
    List<QuizAnswer> findAnswersByQuestion(QuizQuestion q);
}
