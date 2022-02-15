package org.hatulmadan.site.server.application.data.repositories;

import org.hatulmadan.site.server.application.data.entities.security.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityDAO extends CrudRepository<Authority, Long> {
    Authority findByName(String name);
}
