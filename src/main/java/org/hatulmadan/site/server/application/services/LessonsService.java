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

    public List<LessonProxy> findLessonsData(Long groupId, boolean showAll){
        HashSet<Group> groupList = groupsDAO.findByIsDeletedFalse();
        List<LessonProxy> result = new ArrayList<>();
        List<Lesson> lessons;
        if (showAll)
            lessons = dao.findByIsDeletedFalse();
        else {
            lessons = dao.findByGroup(groupId);
//            if (groupId == null)
//                lessons = dao.findByIsDeletedFalse();
//            else
//                lessons = dao.findByGroupIdAndIsDeletedFalseOrderByStart(groupId);
        }
        for (Lesson lesson : lessons){
            LessonProxy proxy = new LessonProxy(lesson);
            Optional<Group> curGroup = groupList.stream().filter(group -> group.getId().equals(lesson.getGroupId())).findFirst();
            proxy.setGroup(curGroup.orElse(null));
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
        Lesson entity = proxy.updateEntity();
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

}
