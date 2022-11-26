package org.hatulmadan.site.server.application.services;

import org.hatulmadan.site.server.application.data.proxies.UserProxy;
import org.hatulmadan.site.server.application.AppConfig;
import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.entities.security.Authority;
import org.hatulmadan.site.server.application.data.entities.security.User;
import org.hatulmadan.site.server.application.data.repositories.AuthorityDAO;
import org.hatulmadan.site.server.application.data.repositories.GroupsDAO;
import org.hatulmadan.site.server.application.data.repositories.UserDAO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PasswordEncoder userPasswordEncoder;

    @Autowired
    AuthorityDAO aDAO;
    
   	 private static final Base64.Decoder decoder = Base64.getDecoder();
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        return user;
    }

    public void saveUser(User user) throws Exception {
        if (user.getPassword() != null || !user.getPassword().startsWith("$"))
            user.setPassword(userPasswordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    public String changePassword(User user, String newPassword) throws Exception {
        user.setPassword(newPassword);
        saveUser(user);
        return "";
    }

    public boolean userExists(String login) {
        return userDAO.findByUsername(login) != null;
    }

    public void updateUser(User user, UserProxy userProxy) {
        if (userProxy.getPassword() != null) {
            if (!userProxy.getPassword().equalsIgnoreCase(""))
                user.setPassword(userProxy.getPassword());
        }
        //Collection<Authority> alist = user.getAuthorities();
        List<Authority> alist = new ArrayList<>();
        for (String authName : userProxy.getAuthorities()) {
            Authority a = aDAO.findByName(authName);
            //	if (!alist.contains(a))
            alist.add(a);
        }
        user.setAuthorities(alist);
    }

    public User createUser(UserProxy userProxy) {
        User user = new User();
        user.setUsername(userProxy.getUserName());
        user.activate();
        user.setPassword(userProxy.getPassword());
        if (userProxy.getAuthorities() != null) {
            List<Authority> alist = new ArrayList<>();
            for (String authName : userProxy.getAuthorities()) {
                Authority a = aDAO.findByName(authName);
                alist.add(a);
            }
            user.setAuthorities(alist);
        }
        if (user.getPassword() != null || !user.getPassword().startsWith("$"))
            user.setPassword(userPasswordEncoder.encode(user.getPassword()));
        return userDAO.save(user);
    }
    public static String decodeUser(String authToken) {
    	if (authToken == null) return "server";
    	String[] tokens = authToken.split(" ");
    	String token = authToken;
    	if (tokens.length == 2)
    		token = tokens[1];
    	String[] chunks = token.split("\\.");

    	String payload = new String(decoder.decode(chunks[1].replace('-', '+').replace('_', '/')));
    	JSONObject info = AppConfig.gson.fromJson(payload, JSONObject.class);
    	return (String) info.get("sub");
   
    }
    
    public List<Group> fetchUsersGroups(String userName) {
       //String userName = decodeUser(token);
       User user=userDAO.findByUsername(userName);
    
        return user.getGroups();
    }
    public boolean isSuper(String userName) {
    	
    	User user=userDAO.findByUsername(userName);   
    	Collection<Authority> authorities = user.getAuthorities();
    	for (Authority a: authorities) {
    		if (a.getName().equalsIgnoreCase("super")) return true;
    	}
    	return false;
    }
}
