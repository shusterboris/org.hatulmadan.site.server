package org.hatulmadan.site.server.application.controllers;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

import org.hatulmadan.site.server.application.data.entities.Article;
import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.entities.courses.Lesson;
import org.hatulmadan.site.server.application.data.entities.courses.Payments;
import org.hatulmadan.site.server.application.data.entities.security.Authority;
import org.hatulmadan.site.server.application.data.entities.security.User;
import org.hatulmadan.site.server.application.data.proxies.ArticleProxy;
import org.hatulmadan.site.server.application.data.proxies.PaymentProxy;
import org.hatulmadan.site.server.application.data.proxies.UserProxy;
import org.hatulmadan.site.server.application.data.repositories.GroupsDAO;
import org.hatulmadan.site.server.application.data.repositories.PaymentDAO;
import org.hatulmadan.site.server.application.data.repositories.UserDAO;
import org.hatulmadan.site.server.application.services.LogService;
import org.hatulmadan.site.server.application.utils.DAOErrorProcess;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author innai
 * управление пользователями
 * платежи пользователей
 */
@RestController
@Getter
@Setter
public class UserController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    LogService logSrv;
    @Autowired
    PaymentDAO pDAO;
    @Autowired
    GroupsDAO gDAO;
// не используется
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
    
    @GetMapping(value = "/users/getAll")
    public ResponseEntity<Object> fetchAllUsers(){
       List<UserProxy> res= new  ArrayList<UserProxy> ();
    	try {
          Iterable<User> users = userDAO.findAll();
            users.forEach(user->
            	res.add(new UserProxy(user.getId(),user.getUsername(), user.getFullname(), user.getPhone(), user.getNote() ) ));
            return new ResponseEntity<>(res, HttpStatus.OK);
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
    public ResponseEntity<Object> saveUser(@RequestBody String json) {
        try {
        	//{"note":"","phone":"","id":13,"fullname":"re","authorities":super}
            Object obj = new JSONParser().parse(json);
            JSONObject jo = (JSONObject) obj;
            Long id = (Long) jo.get("id");
            Optional<User> u = userDAO.findById(id);
            if (u.isPresent()) {
            	User res=u.get();
            	String fullname = (String) jo.get("fullname");
            	res.setFullname(fullname);
            	String note = (String) jo.get("note");
            	res.setNote(note);
            	String phone = (String) jo.get("phone");
            	res.setPhone(phone);
            	String lst=(String) jo.get("authorities");
            	List<Authority> alist = new ArrayList<>();
    	        
            	if (lst.equalsIgnoreCase("super")) {
            		Authority a=new Authority();
            		a.setId((long) 1);
            		a.setName(lst);
            		alist.add(a);
            	}
	            
            	res.setAuthorities(alist);
	            res=userDAO.save(res);
	        	return new ResponseEntity<>("", HttpStatus.OK);
        	} else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        }catch (Exception e){
        	  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(value = "/users/getById/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id){
        try {
            Optional<User> u = userDAO.findById(id);
            if (u.isPresent()) {
            	User res=u.get();
            	res.setPassword("");
                return new ResponseEntity<>(res, HttpStatus.OK);
            }else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return DAOErrorProcess.processError(e, logSrv, null);
        }
    }
    //----------------------------------платежи-------------------------
    //все платежи по юзеру
    @GetMapping(value = "users/getInfo/{id}")
    public ResponseEntity<Object> fetchPaymentsById(@PathVariable Long id){
        try {
        	//List<Payments> res = pDAO.findByUserId(id);
        	List<PaymentProxy> res=new ArrayList<PaymentProxy>();
        	List<Object[]> resO = pDAO.findByUserIdWithGroup(id);
        	for (Object[] o : resO) {
        		res.add(new PaymentProxy((Payments)o[0],(String)o[1]));
        	}
            return new ResponseEntity<>(res,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //платежи по группе
    @GetMapping(value = "users/getInfoByGroup/{id}")
    public ResponseEntity<Object> fetchPaymentsByGroupId(@PathVariable Long id){
        try {
            //выбираем всех юзеров из группы и считаем платежи
        	Optional<Group> g = gDAO.findById(id);
        	if(!g.isPresent()) {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        	List<String> payList=new ArrayList<String> ();
        	List<User> l=g.get().getUsers();
        	for (User u : l) {
        		 int sum= pDAO.countByGroupIdAndUserId(id,u.getId());
        		//res.forEach(pay->sum=sum+pay.getPsum());
            	payList.add(u.getUsername()+": всего платежей "+String.valueOf(sum));
        	}
        	
             return new ResponseEntity<>(payList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(value = "/users/paymentsave")
    public ResponseEntity<Object> savePayment(@RequestBody PaymentProxy proxy){
        try{
        	Payments p=null;
        	if (proxy.getId()==null) {
        	   p = pDAO.save(proxy.createPayment());
        	} else {
        		p=pDAO.findById(proxy.getId()).get();
        		p.setComment(proxy.getComment());
        		p.setGroupId(Long.valueOf(proxy.getGroupName()));
        		p.setPdate(proxy.getPdate());
        		p.setPsum(proxy.getPsum());
        		 p = pDAO.save(p);
        	}
            return new ResponseEntity<>(p.getId(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
     
}
