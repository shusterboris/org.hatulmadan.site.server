package org.hatulmadan.site.server.application.controllers;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.security.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@Getter
@Setter
public class UserController {
    @GetMapping(value = "user/getByName/{username}")
    public ResponseEntity<Object> getByName(@PathVariable("username") String  username, Authentication authentication){
        List<String> fakeUsernames = Arrays.asList("aron","tanya","vasya");
        User user;
        Optional<String> found = fakeUsernames.stream().filter(current -> current.equals(username)).findFirst();
        if (found.isPresent()){
            user = new User();
            user.setUsername(username);
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
