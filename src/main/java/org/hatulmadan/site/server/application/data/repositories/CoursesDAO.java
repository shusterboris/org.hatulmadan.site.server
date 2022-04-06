package org.hatulmadan.site.server.application.data.repositories;

import org.hatulmadan.site.server.application.data.entities.courses.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoursesDAO extends CrudRepository<Course, Long> {
    List<Course> findByIsDeletedFalseOrderBySortOrder();
    List<Course> findAll();
}
