package org.hatulmadan.site.server.application.data.repositories;

import org.hatulmadan.site.server.application.data.entities.courses.Lesson;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LessonsDAO extends CrudRepository<Lesson, Long> {
    List<Lesson> findAll();
}
