package org.hatulmadan.site.server.application.services;

import org.hatulmadan.site.server.application.data.entities.proxies.UserProxy;
import org.hatulmadan.site.server.application.data.entities.security.Authority;
import org.hatulmadan.site.server.application.data.entities.security.User;
import org.hatulmadan.site.server.application.data.repositories.AuthorityDAO;
import org.hatulmadan.site.server.application.data.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PasswordEncoder userPasswordEncoder;

    @Autowired
    AuthorityDAO aDAO;

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

}
