package org.hatulmadan.site.server.application.controllers;

import org.hatulmadan.site.server.application.data.entities.survey.Quiz;
import org.hatulmadan.site.server.application.data.entities.survey.QuizAnswer;
import org.hatulmadan.site.server.application.data.entities.survey.QuizQuestion;
import org.hatulmadan.site.server.application.data.proxies.LessonProxy;
import org.hatulmadan.site.server.application.data.proxies.SurveyProxy;
import org.hatulmadan.site.server.application.data.proxies.SurveyQuestionProxy;
import org.hatulmadan.site.server.application.data.repositories.QuestionsDAO;
import org.hatulmadan.site.server.application.data.repositories.SurveyDAO;
import org.hatulmadan.site.server.application.services.SurveysService;
import org.hatulmadan.site.server.application.utils.DAOErrorProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SurveysController {
    @Autowired
    SurveyDAO surveyDAO;
    @Autowired
    QuestionsDAO qDAO;
    @Autowired
    SurveysService service;

    @GetMapping(value = "survey/list")
    public ResponseEntity<Iterable<Quiz>> fetchQuizList(){
        Iterable<Quiz> res = surveyDAO.findAll();
        return new ResponseEntity<Iterable<Quiz>>(res, HttpStatus.OK);
    }

    @GetMapping(value = "survey/byId/{id}")
    public ResponseEntity<SurveyProxy> fetchSurveyById(@PathVariable Long id){
        SurveyProxy proxy = service.fetchSurvey(id);
        return new ResponseEntity<>(proxy, HttpStatus.OK);
    }

    @GetMapping(value = "survey/questionById/{id}")
    public ResponseEntity<SurveyQuestionProxy> fetchSurveyQuestionById(@PathVariable Long id){
        SurveyQuestionProxy question = service.fetchSurveyQuestionById(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }
    
    @PostMapping(value = "survey/surveysave")
    public ResponseEntity<Object> saveSurvey(@RequestBody SurveyProxy proxy){
        try{
            Long id = service.saveSurvey(proxy);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
