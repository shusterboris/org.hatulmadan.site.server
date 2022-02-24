package org.hatulmadan.site.server.application.controllers;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.config.JwtTokenUtil;
import org.hatulmadan.site.server.application.data.entities.security.JwtRequest;
import org.hatulmadan.site.server.application.data.entities.security.JwtResponse;
import org.hatulmadan.site.server.application.data.entities.security.User;
import org.hatulmadan.site.server.application.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Getter
@Setter
public class UserAuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl jwtUserDetailsService;

    @PostMapping(value = "/auth", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        if (authenticationRequest.getNewPassword() != null) {
            String result = jwtUserDetailsService.changePassword((User) userDetails, authenticationRequest.getNewPassword());
            if (!"".equals(result))
                throw new Exception("ANOTHER_REASON");
        }
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
    
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerNewUser(@RequestBody JwtRequest authenticationRequest) throws Exception {
        User user = new User();
        user.setUsername(authenticationRequest.getUsername());
        user.setPassword(authenticationRequest.getPassword());
        try {
            jwtUserDetailsService.saveUser(user);
            final String token = jwtTokenUtil.generateToken(user);
            return ResponseEntity.ok(new JwtResponse(token));
        }catch (Exception e){
            return new ResponseEntity<String>("",HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (LockedException e) {
            throw new Exception("USER_LOCKED", e);
        } catch (Exception e) {
            throw new Exception("ANOTHER_REASON", e);
        }
    }

}
