package org.hatulmadan.site.server.application.data.entities.security;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 606006963693488097L;
    private String username;
    private String password;
    private String newPassword;

    // need default constructor
    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public JwtRequest(String username, String password, String newPassword) {
        this.setUsername(username);
        this.setPassword(password);
        this.setNewPassword(newPassword);
    }

}
