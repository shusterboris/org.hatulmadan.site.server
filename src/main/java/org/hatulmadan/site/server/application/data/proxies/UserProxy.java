package org.hatulmadan.site.server.application.data.proxies;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

@Getter
@Setter
public class UserProxy {
     private Long id;
	 private String userName;
     private List<String> authorities = new ArrayList<>();
     private String password;
     private String fullname="";
     private String phone="";
     private String note="";

    public UserProxy(Long id,String userName) {
        this.userName = userName;
        this.id=id;
      
    }
    public UserProxy(Long id,String userName, String fullName, String phone, String note ) {
        this.userName = userName;
        if (fullName!= null) this.fullname = fullName;
        if (phone!= null)this.phone=phone;
        if (note!= null)this.note=note;
        this.id=id;
    }
    
}
