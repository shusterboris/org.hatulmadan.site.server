package org.hatulmadan.site.server.application.services;

import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.entities.courses.Lesson;
import org.hatulmadan.site.server.application.data.entities.courses.Materials;
import org.hatulmadan.site.server.application.data.proxies.LessonProxy;
import org.hatulmadan.site.server.application.data.repositories.GroupsDAO;
import org.hatulmadan.site.server.application.data.repositories.LessonsDAO;
import org.hatulmadan.site.server.application.data.repositories.MaterialsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LessonsService {
    @Autowired
    LessonsDAO dao;

    @Autowired
    MaterialsDAO mDAO;

    @Autowired
    GroupsDAO groupsDAO;
    
    @Autowired
    UserDetailsServiceImpl userDetail;

    public List<LessonProxy> findLessonsData(Long groupId, boolean showAll, String token){
        List<LessonProxy> result = new ArrayList<>();
        List<Lesson> lessons;
        String username=userDetail.decodeUser(token);
        List<Group> usersGr = userDetail.fetchUsersGroups(username);
        boolean supervisor=userDetail.isSuper(username);
        HashSet<Group> groupList = new HashSet<>();
        		//all 
        if (!supervisor) {
        	//for(Group g:usersGr)
        	   groupList.addAll(usersGr);
        } else 
        	groupList=groupsDAO.findByIsDeletedFalse();
        if (showAll)
            lessons = dao.findByIsDeletedFalseOrderByStartDesc();
 
        else {
            lessons = dao.findByGroup(groupId);
        }
        for (Lesson lesson : lessons){
            LessonProxy proxy = new LessonProxy(lesson);
            Optional<Group> curGroup = groupList.stream().filter(group -> group.getId().equals(lesson.getGroupId())).findFirst();
            proxy.setGroup(curGroup.orElse(null));
            // добавить проверку есть ли у юзера эта группа
            List<Materials> materials = mDAO.findByLesson(lesson.getId());
            proxy.setMaterials(materials);
            result.add(proxy);
        }
        return result;
    }

    private void saveMaterials(Materials m, Long lessonId){
        m.setLesson(lessonId);
        mDAO.save(m);
    }

    public Long saveLesson(LessonProxy proxy) throws Exception{
        Optional<Lesson> existing = null;
        Lesson entity = null;
        if (proxy.getId() != null) {
            existing = dao.findById(proxy.getId());
            entity = proxy.updateEntity(existing.orElse(null));
        }else
            entity = proxy.updateEntity(null);
        Lesson result = dao.save(entity);
        proxy.getMaterials().stream()
                .filter(m->m.getId()==null)
                .forEach((Materials m) ->saveMaterials(m, result.getId()));
        return result.getId();
    }

    public LessonsDAO getDao() {
        return dao;
    }

    public MaterialsDAO getmDAO() {return mDAO; }
    
    public GroupsDAO getGDAO() {
    	return groupsDAO;
    };

}
