package org.hatulmadan.site.server.application.controllers;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.hatulmadan.site.server.application.data.entities.security.User;
import org.hatulmadan.site.server.application.data.repositories.UserDAO;
import org.hatulmadan.site.server.application.services.LogService;
import org.hatulmadan.site.server.application.utils.DAOErrorProcess;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import java.time.format.DateTimeFormatter;
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
    
    @PostMapping(value = "/users/delete")
    public ResponseEntity<String> deleteUser(@RequestBody String json) {
        try {
			/*
			 * JSONParser parser = new JSONParser();
			 * 
			 * try { JSONObject json = (JSONObject) parser.parse(requestRes); JSONObject jW
			 * = (JSONObject) json.get("get_worker_info");
			 */
            
        	return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e){
        	  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }	
        
    @PostMapping(value = "/users/save")
    public ResponseEntity<String> saveUser(@RequestBody String json) {
        try {
            //UserProxy userProxy = new UserProxy(json, UserProxy.class);
            return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e){
        	  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
