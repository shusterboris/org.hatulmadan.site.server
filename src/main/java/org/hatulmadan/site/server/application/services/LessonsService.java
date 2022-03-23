package org.hatulmadan.site.server.application.services;

import org.hatulmadan.site.server.application.data.entities.courses.Lesson;
import org.hatulmadan.site.server.application.data.entities.courses.Materials;
import org.hatulmadan.site.server.application.data.proxies.LessonProxy;
import org.hatulmadan.site.server.application.data.repositories.LessonsDAO;
import org.hatulmadan.site.server.application.data.repositories.MaterialsDAO;
import org.hatulmadan.site.server.application.utils.DAOErrorProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LessonsService {
    @Autowired
    LessonsDAO dao;
    @Autowired
    MaterialsDAO mDAO;

    public LessonsDAO getDao() {
        return dao;
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
}
