package org.hatulmadan.site.server.application.data.entities.security;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JwtResponse implements Serializable {
    private final String jwttoken;
    private User user;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public JwtResponse(String jwttoken, User user) {
        this.jwttoken = jwttoken;
        this.user = user;
    }

}
