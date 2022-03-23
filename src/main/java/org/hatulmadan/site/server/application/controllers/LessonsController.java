package org.hatulmadan.site.server.application.controllers;

import org.hatulmadan.site.server.application.data.entities.courses.Lesson;
import org.hatulmadan.site.server.application.data.proxies.LessonProxy;
import org.hatulmadan.site.server.application.services.LessonsService;
import org.hatulmadan.site.server.application.utils.DAOErrorProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LessonsController {
    @Autowired
    LessonsService service;

    @GetMapping(value = "lesson/getById/{id}")
    public ResponseEntity fetchLessonById(@PathVariable Long id){
        try {
            Optional<Lesson> res = service.getDao().findById(id);
            return res.map(lesson -> new ResponseEntity(lesson, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "lesson/getAll")
    public ResponseEntity fetchLessons(){
        try{
            List<Lesson> res = service.getDao().findAll();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "lesson/save")
    public ResponseEntity<Object> saveLesson(@RequestBody LessonProxy proxy){
        try{
            Long id = service.saveLesson(proxy);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }catch (Exception e){
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
