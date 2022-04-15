package org.hatulmadan.site.server.application.controllers;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.hatulmadan.site.server.application.data.entities.security.User;
import org.hatulmadan.site.server.application.data.repositories.UserDAO;
import org.hatulmadan.site.server.application.services.LogService;
import org.hatulmadan.site.server.application.utils.DAOErrorProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Getter
@Setter
public class UserController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    LogService logSrv;

    @GetMapping(value = "/users/getActive")
    public ResponseEntity<Object> fetchActiveUsers(){
        try {
            List<User> users = userDAO.findByEnabledTrue();
            users.forEach(user->user.setPassword(null));
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception e){
            return DAOErrorProcess.processError(e, logSrv, null);
        }
    }
}
