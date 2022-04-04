package org.hatulmadan.site.server.application.data.repositories;

import org.hatulmadan.site.server.application.data.entities.courses.Materials;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MaterialsDAO extends CrudRepository<Materials, Long> {
    List<Materials> findByLesson(Long id);
}
