package org.hatulmadan.site.server.application.data.repositories;

import org.hatulmadan.site.server.application.data.entities.security.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
