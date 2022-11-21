package org.hatulmadan.site.server.application.data.proxies;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserProxy {
    private String userName;
     private List<String> authorities = new ArrayList<>();
     private String password;
     

    public UserProxy(String userName) {
        this.userName = userName;
      
    }

    
}
