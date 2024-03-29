package org.hatulmadan.site.server.application.controllers;

import org.hatulmadan.site.server.application.AppConfig;
import org.hatulmadan.site.server.application.data.entities.courses.Lesson;
import org.hatulmadan.site.server.application.data.entities.courses.Materials;
import org.hatulmadan.site.server.application.data.proxies.ArticleProxy;
import org.hatulmadan.site.server.application.data.proxies.LessonProxy;
import org.hatulmadan.site.server.application.services.AttachmentsService;
import org.hatulmadan.site.server.application.services.LessonsService;
import org.hatulmadan.site.server.application.utils.DAOErrorProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class LessonsController {
    @Autowired
    LessonsService service;
    
    @Autowired
    AttachmentsService attServie;

    @GetMapping(value = "lesson/getById/{id}")
    public ResponseEntity<Object> fetchLessonById(@PathVariable Long id){
        try {
            Optional<Lesson> res = service.getDao().findById(id);
            return res.map(lesson -> new ResponseEntity<Object>(lesson, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "lesson/getAll")
    public ResponseEntity<Object> fetchLessons(@RequestHeader(name = "Authorization") String token){
        try{
            List<LessonProxy> res = service.findLessonsData(null, true,token);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = {"lesson/getByGroupId/{groupId}", "lesson/getByGroupId"} )
    public ResponseEntity<Object> fetchLessonsByGroup(@PathVariable(required = false) Long groupId,@RequestHeader(name = "Authorization") String token){
        try{
            List<LessonProxy> res = service.findLessonsData(groupId, false, token);
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
            e.printStackTrace();
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "lesson/materials/save/{lessonId}")
    public ResponseEntity<Object> saveLessonsMaterials(@PathVariable Long lessonId, @RequestBody Materials materials) {
        try{
            service.getmDAO().save(materials);
            List<Materials> updatedList = service.getmDAO().findByLesson(lessonId);
            return new ResponseEntity<>(updatedList, HttpStatus.OK);
        }catch (Exception e){
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "lesson/materials/delete/{id}")
    public ResponseEntity<Object> removeLessonsMaterials(@PathVariable Long id) {
        try{
            Optional<Materials> found = service.getmDAO().findById(id);
            if (!found.isPresent()){
                return new ResponseEntity<>("Материал с указанным идентификатором не найден", HttpStatus.NOT_FOUND);
            }
            Long lessonId = found.get().getLesson();
            service.getmDAO().deleteById(id);
            List<Materials> updatedList = service.getmDAO().findByLesson(lessonId);
            return new ResponseEntity<>(updatedList, HttpStatus.OK);
        }catch (Exception e){
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "lesson/materials/getByLessonId/{lessonId}")
    public ResponseEntity<Object> fetchLessonsMaterials(@PathVariable Long lessonId) {
        try{
            List<Materials> list = service.getmDAO().findByLesson(lessonId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }catch (Exception e){
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "lesson/materials/getAttFile/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> fetchMaterialsAttachmentByName(@PathVariable String fileName){
        try {
            byte[] fileBody = attServie.readImage(fileName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(fileBody);
        }catch (NoSuchFileException nsfe) {
            String errMsg = "Нет запрошенного вложенного файла на диске!";
            AppConfig.log.error(errMsg);
            return new ResponseEntity<>(errMsg, HttpStatus.NOT_FOUND);
        }catch (IOException ioe){
            String errMsg = "Ошибка при чтении файла!";
            AppConfig.log.error(errMsg);
            return new ResponseEntity<>(errMsg, HttpStatus.NO_CONTENT);
        }catch (Exception e){
            AppConfig.log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "lesson/copy")
    public ResponseEntity<Object> copyLesson( @RequestBody LessonProxy lesson) {
        try{
        	List<Materials> materialsList = service.getmDAO().findByLesson(lesson.getId());
            lesson.setId(null);
            Long nl=service.saveLesson(lesson);
          for (Materials x : materialsList) {
        	  Materials c=new Materials();
        	  c.setLesson(nl);
        	  c.setComment(x.getComment());
        	  c.setFileLink(x.getFileLink());
        	  c.setSrvFileLink(x.getSrvFileLink());
        	  c.setYoutubeLink(x.getYoutubeLink());
        	  
        	  service.getmDAO().save(c);
          }
        
            return new ResponseEntity<>(nl, HttpStatus.OK);
        }catch (Exception e){
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
}
