package org.hatulmadan.site.server.application.data.repositories;

import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.List;

public interface GroupsDAO extends CrudRepository<Group, Long> {
    List<Group> findAll();
    HashSet<Group> findByIsDeletedFalse();
}
