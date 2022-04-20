package org.hatulmadan.site.server.application.data.repositories;

import org.hatulmadan.site.server.application.data.entities.courses.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonsDAO extends CrudRepository<Lesson, Long> {
    List<Lesson> findAll();
    List<Lesson> findByIsDeletedFalse();
    List<Lesson> findByGroupIdAndIsDeletedFalseOrderByStart(Long groupId);
    @Query("select l from Lesson l where l.groupId = ?1 or l.groupId=null order by sortOrder asc, start desc")
    List<Lesson> findByGroup(Long groupId);
}
