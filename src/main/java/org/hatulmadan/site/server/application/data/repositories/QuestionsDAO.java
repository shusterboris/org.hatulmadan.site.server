package org.hatulmadan.site.server.application.data.repositories;

import org.hatulmadan.site.server.application.data.entities.survey.Quiz;
import org.hatulmadan.site.server.application.data.entities.survey.QuizQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionsDAO extends CrudRepository<QuizQuestion, Long> {
    List<QuizQuestion> findByQuizAndIsDeletedFalse(Long surveyId);
    List<QuizQuestion> findByQuizAndIsDeletedFalseOrderBySortOrderAsc(Quiz quiz);

}
