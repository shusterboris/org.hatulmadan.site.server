package org.hatulmadan.site.server.application.controllers;

import org.hatulmadan.site.server.application.data.entities.courses.Course;
import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.proxies.GroupProxy;
import org.hatulmadan.site.server.application.data.repositories.CoursesDAO;
import org.hatulmadan.site.server.application.data.repositories.GroupsDAO;
import org.hatulmadan.site.server.application.services.LogService;
import org.hatulmadan.site.server.application.utils.DAOErrorProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DictController {
    @Autowired
    GroupsDAO groupsDAO;

    @Autowired
    CoursesDAO coursesDAO;

    @Autowired
    LogService logSrv;

    @GetMapping(value = "/dictionary/group/getAll")
    public ResponseEntity<Object> fetchGroupsAll(){
        try {
            List<Group> result = groupsDAO.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            logSrv.logError(e);
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/dictionary/group/getById/{id}")
    public ResponseEntity<Object> fetchGroupsById(@PathVariable Long id){
        try {
            Optional<Group> result = groupsDAO.findById(id);
            if (result.isPresent()) {
                GroupProxy proxy = new GroupProxy(result.get());
                return new ResponseEntity<>(proxy, HttpStatus.OK);
            }else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logSrv.logError(e);
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/dictionary/group/save")
    public ResponseEntity<Object> saveGroupInfo(@RequestBody Group group){
        try{
            if (group.getId() != null) {
                Optional<Group> found = groupsDAO.findById(group.getId());
                if (found.isPresent()) {
                    Group updated = found.get();
                    updated.setName(group.getName());
                    updated.setSortOrder(group.getSortOrder());
                    groupsDAO.save(updated);
                }
            }else
                groupsDAO.save(group);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logSrv.logError(e);
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ********************************************************************************
    // **************************** END GROUP ************************************

    @GetMapping(value = "/dictionary/course/getActive")
    public ResponseEntity<Object> fetchCourses(){
        try {
            List<Course> result = coursesDAO.findByIsDeletedFalseOrderBySortOrder();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            logSrv.logError(e);
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/dictionary/course/remove/{id}")
    public ResponseEntity<Object> removeCourse(@PathVariable Long id){
        try {
            Optional<Course> courseOpt = coursesDAO.findById(id);
            if (!courseOpt.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            coursesDAO.delete(courseOpt.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            logSrv.logError(e);
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/dictionary/course/save")
    public ResponseEntity<Object> saveCourse(@RequestBody Course course){
        try{
            Course savedCourse;
            if (course.getId() != null) {
                Optional<Course> found = coursesDAO.findById(course.getId());
                if (found.isPresent()) {
                    Course updated = found.get();
                    updated.setName(course.getName());
                    updated.setSortOrder(course.getSortOrder());
                    coursesDAO.save(updated);
                }
            }else
                coursesDAO.save(course);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logSrv.logError(e);
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
