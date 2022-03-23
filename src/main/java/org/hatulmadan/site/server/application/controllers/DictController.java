package org.hatulmadan.site.server.application.controllers;

import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.repositories.GroupsDAO;
import org.hatulmadan.site.server.application.utils.DAOErrorProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DictController {
    @Autowired
    GroupsDAO groupsDAO;

    @GetMapping(value = "/dictionary/group/getAll")
    public ResponseEntity<Object> fetchGroupsAll(){
        try {
            List<Group> result = groupsDAO.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
