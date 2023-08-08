package org.hatulmadan.site.server.application.controllers;

import org.hatulmadan.site.server.application.data.entities.Article;
import org.hatulmadan.site.server.application.data.entities.courses.Course;
import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.entities.security.User;
import org.hatulmadan.site.server.application.data.proxies.ArticleProxy;
import org.hatulmadan.site.server.application.data.proxies.GroupProxy;
import org.hatulmadan.site.server.application.data.repositories.ArticleDAO;
import org.hatulmadan.site.server.application.data.repositories.CoursesDAO;
import org.hatulmadan.site.server.application.data.repositories.GroupsDAO;
import org.hatulmadan.site.server.application.data.repositories.UserDAO;
import org.hatulmadan.site.server.application.services.AttachmentsService;
import org.hatulmadan.site.server.application.services.LogService;
import org.hatulmadan.site.server.application.utils.DAOErrorProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author innai
 * группы и новости
 */
@RestController
public class DictController {
    @Autowired
    GroupsDAO groupsDAO;

    @Autowired
    CoursesDAO coursesDAO;

    @Autowired
    LogService logSrv;

    @Autowired
    UserDAO userDAO;
    
    @Autowired
    ArticleDAO atDAO;
    @Autowired
    AttachmentsService atas;

    @GetMapping(value = "/dictionary/group/getAll/{full}")
    public ResponseEntity<Object> fetchGroupsAll(@PathVariable boolean full){
        try {
            List<Group> result = groupsDAO.findAll();
            if (full) {
            	return new ResponseEntity<>(result, HttpStatus.OK);
            }else {
            	List<GroupProxy> shortRes=new ArrayList<GroupProxy>();
            	for (Group g:result){
            		shortRes.add(new GroupProxy(g.getId(),g.getName()));
            	}
            	return new ResponseEntity<>(shortRes, HttpStatus.OK);
            }
            
        } catch (Exception e){
            return DAOErrorProcess.processError(e, logSrv, null);
        }
    }
    
    @GetMapping(value = "/dictionary/group/getById/{id}")
    public ResponseEntity<Object> fetchGroupsById(@PathVariable Long id){
        try {
            Optional<Group> result = groupsDAO.findById(id);
            if (result.isPresent()) {
                GroupProxy proxy = new GroupProxy(result.get());
                return new ResponseEntity<>(proxy, HttpStatus.OK);
            }else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return DAOErrorProcess.processError(e, logSrv, null);
        }
    }

    @PutMapping(value = "/dictionary/group/save")
    public ResponseEntity<Object> saveGroupInfo(@RequestBody Group group){
        try{
            if (group.getId() != null) {
                Optional<Group> found = groupsDAO.findById(group.getId());
                if (found.isPresent()) {
                    Group updated = found.get();
                    updated.setName(group.getName());
                    updated.setSortOrder(group.getSortOrder());
                    updated.setStartCourceDate(group.getStartCourceDate());
                    updated.setEndCourceDate(group.getEndCourceDate());
                    updated.setPrice(group.getPrice());
                    groupsDAO.save(updated);
                }
            }else
                groupsDAO.save(group);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            String errClass = e.getClass().getCanonicalName();
            if (!errClass.contains("DataIntegrityViolationException"))
                return DAOErrorProcess.processError(e, logSrv, HttpStatus.INTERNAL_SERVER_ERROR);
            else
                return new ResponseEntity<>("Название группы должно быть уникальным!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/dictionary/group/addUser/{userId}/{groupId}")
    public ResponseEntity<Object> addUserToGroup(@PathVariable Long userId, @PathVariable Long groupId){
        try{
            Optional<User> userOpt = userDAO.findById(userId);
            if (!userOpt.isPresent())
                return new ResponseEntity<>("Не найден пользователь", HttpStatus.NOT_FOUND);
            Optional<Group> groupOpt = groupsDAO.findById(groupId);
            if (!groupOpt.isPresent())
                return new ResponseEntity<>("Не найдена группа", HttpStatus.NOT_FOUND);
            User user = userOpt.get();
            //группа есть в списке групп пользователя, т.е. фактически ее добавлять не нужно, но это и не ошибка, есть, так есть
            Optional<Group> found = user.getGroups().stream().filter(group -> group.getId().equals(groupId)).findAny();
            if (found.isPresent())
                return new ResponseEntity<>(user.getGroups(),HttpStatus.OK);
            List<Group> groups = user.getGroups();
            groups.add(groupOpt.get());
            user.setGroups(groups);
            userDAO.save(user);
            List<User> newUserList = groupsDAO.findById(groupId).get().getUsers();
            return new ResponseEntity<>(newUserList, HttpStatus.OK);
        }catch (Exception e){
            return DAOErrorProcess.processError(e, logSrv, null);
        }
    }

    @PutMapping(value = "/dictionary/group/excludeUser/{userId}/{groupId}")
    public ResponseEntity<Object> excludeUserFromGroup(@PathVariable Long userId, @PathVariable Long groupId){
        try{
            Optional<User> userOpt = userDAO.findById(userId);
            if (!userOpt.isPresent())
                return new ResponseEntity<>("Не найден пользователь", HttpStatus.NOT_FOUND);
            Optional<Group> groupOpt = groupsDAO.findById(groupId);
            if (!groupOpt.isPresent())
                return new ResponseEntity<>("Не найдена группа", HttpStatus.NOT_FOUND);
            User user = userOpt.get();
            //оставляем у пользователя только те группы, id которых не равен удаляемому
            List<Group> groups = user.getGroups().stream().filter(usr -> !usr.getId().equals(groupId)).collect(Collectors.toList());
            user.setGroups(groups);
            userDAO.save(user);
            List<User> newUserList = groupsDAO.findById(groupId).get().getUsers();
            return new ResponseEntity<>(newUserList, HttpStatus.OK);
        }catch (Exception e){
            return DAOErrorProcess.processError(e, logSrv, null);
        }
    }
    // ********************************************************************************
    // **************************** END GROUP ************************************

    @GetMapping(value = "/dictionary/course/getActive")
    public ResponseEntity<Object> fetchCourses(){
        try {
            List<Course> result = coursesDAO.findByIsDeletedFalseOrderBySortOrder();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            return DAOErrorProcess.processError(e, logSrv, null);
        }
    }

    @DeleteMapping(value = "/dictionary/course/remove/{id}")
    public ResponseEntity<Object> removeCourse(@PathVariable Long id){
        try {
            Optional<Course> courseOpt = coursesDAO.findById(id);
            if (!courseOpt.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            coursesDAO.delete(courseOpt.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return DAOErrorProcess.processError(e, logSrv, null);
        }
    }

    @PutMapping(value = "/dictionary/course/save")
    public ResponseEntity<Object> saveCourse(@RequestBody Course course){
        try{
            Course savedCourse;
            if (course.getId() != null) {
                Optional<Course> found = coursesDAO.findById(course.getId());
                if (found.isPresent()) {
                    Course updated = found.get();
                    updated.setName(course.getName());
                    updated.setSortOrder(course.getSortOrder());
                    coursesDAO.save(updated);
                }
            }else
                coursesDAO.save(course);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            String errClass = e.getClass().getCanonicalName();
            if (!errClass.contains("DataIntegrityViolationException"))
                return DAOErrorProcess.processError(e, logSrv, null);
            else
                return new ResponseEntity<>("Курс с таким названием уже существует. Дубликаты запрещены", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //******************articles*******************
    @PutMapping(value = "article/save")
    public ResponseEntity<Object> saveArticle(@RequestBody ArticleProxy proxy){
        try{
        	Article a=null;
        	if (proxy.getId()==null) {
        	   a = atDAO.save(proxy.createArticle());
        	} else  {
        		a=atDAO.findById(proxy.getId()).get();
        		a.setLink(proxy.getLink());
        		a.setSrvFileLink(proxy.getSrvFileLink());
        		a.setTextA(proxy.getTextA());
        		a.setTitleA(proxy.getTitleA());
        		a.setType(proxy.getType());
        		 a = atDAO.save(a);
        	}
            return new ResponseEntity<>(a.getId(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            String errMsg = DAOErrorProcess.getErrorMessage(e);
            return new ResponseEntity<>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "getArticles/{full}/{kind}/{start}/{len}")
    public ResponseEntity<Object> fetchArticles(@PathVariable boolean full, @PathVariable String kind,
    		@PathVariable(value="start",required=false) int start, @PathVariable(value="len",required=false) int len
    		){
        try {
            List<ArticleProxy> result =new ArrayList<ArticleProxy>();
            List<Article> at    = (List<Article>) atDAO.findAllByOrderByIdDesc();        	
            int end=start+len;
            if (end>0 & at.size()>start){
            	if (end<at.size()) {
            		at=at.subList(start, end);
            	} else {
            		at=at.subList(start, at.size());
            	}
            }
            for (Article a:at) {
            	ArticleProxy apr=new ArticleProxy(a.getTitleA(),a.getTextA());
            	apr.setId(a.getId());
            	apr.setType(a.getType());
            	apr.setLink(a.getLink());
            	if (full) {
            		if(a.getSrvFileLink()!=null)
            			apr.setImage(atas.readImage(a.getSrvFileLink()));
            	}
            	result.add(apr);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            return DAOErrorProcess.processError(e, logSrv, null);
        }
    }
    @GetMapping(value = "/article/getById/{id}")
    public ResponseEntity<Object> fetchArticleById(@PathVariable Long id){
        try {
            Optional<Article> a = atDAO.findById(id);
            if (a.isPresent()) {
            	ArticleProxy apr=new ArticleProxy(a.get().getTitleA(),a.get().getTextA());
            	apr.setId(a.get().getId());
            	apr.setType(a.get().getType());
            	apr.setLink(a.get().getLink());
            	if(a.get().getSrvFileLink()!=null) {
            			apr.setImage(atas.readImage(a.get().getSrvFileLink()));
            			apr.setSrvFileLink(a.get().getSrvFileLink());
            	}
                return new ResponseEntity<>(apr, HttpStatus.OK);
            }else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return DAOErrorProcess.processError(e, logSrv, null);
        }
    }
    @GetMapping(value = "/getArticles/count/{kind}")
    public ResponseEntity<Object> fetchArticlesCount(@PathVariable  String kind){
    	try {
    	            List<ArticleProxy> result =new ArrayList<ArticleProxy>();
    	            List<Article> at    = (List<Article>) atDAO.findAll();     
    	            return new ResponseEntity<>(at.size(), HttpStatus.OK); 
    	} catch (Exception e){
              return DAOErrorProcess.processError(e, logSrv, null);
          }
      }	  
}
