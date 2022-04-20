package org.hatulmadan.site.server.application.services;

import org.hatulmadan.site.server.application.data.entities.courses.Lesson;
import org.hatulmadan.site.server.application.data.entities.courses.Materials;
import org.hatulmadan.site.server.application.data.entities.survey.Quiz;
import org.hatulmadan.site.server.application.data.entities.survey.QuizAnswer;
import org.hatulmadan.site.server.application.data.entities.survey.QuizQuestion;
import org.hatulmadan.site.server.application.data.proxies.LessonProxy;
import org.hatulmadan.site.server.application.data.proxies.SurveyProxy;
import org.hatulmadan.site.server.application.data.proxies.SurveyQuestionProxy;
import org.hatulmadan.site.server.application.data.repositories.AnswersDAO;
import org.hatulmadan.site.server.application.data.repositories.QuestionsDAO;
import org.hatulmadan.site.server.application.data.repositories.SurveyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveysService {
    @Autowired
    SurveyDAO surveyDAO;
    @Autowired
    QuestionsDAO qDAO;
    @Autowired
    AnswersDAO answersDAO;

    public SurveyProxy fetchSurvey(Long id){
        Optional<Quiz> surveyOpt = surveyDAO.findById(id);
        if (!surveyOpt.isPresent())
            return null;
        Quiz survey = surveyOpt.get();
        SurveyProxy proxy = new SurveyProxy(survey);
        List<QuizQuestion> questionList = qDAO.findByQuizAndIsDeletedFalseOrderBySortOrderAsc(survey);
        List<Long> questionIds = new ArrayList<>();
        questionList.forEach(q->questionIds.add(q.getId()));
        proxy.setQuestionIds(questionIds);
        return proxy;
    }

    public SurveyQuestionProxy fetchSurveyQuestionById(Long id){
        Optional<QuizQuestion> questionOpt = qDAO.findById(id);
        if (!questionOpt.isPresent())
            return null;
        QuizQuestion question = questionOpt.get();
        List<QuizAnswer> answers = answersDAO.findAnswersByQuestion(question);
        SurveyQuestionProxy proxy = new SurveyQuestionProxy(question);
        proxy.setAnswers(answers);
        return proxy;
    }
    public Long saveSurvey(SurveyProxy proxy) throws Exception{
        Quiz entity = proxy.updateEntity();
        Quiz result = surveyDAO.save(entity);
        return result.getId();
    }
}
