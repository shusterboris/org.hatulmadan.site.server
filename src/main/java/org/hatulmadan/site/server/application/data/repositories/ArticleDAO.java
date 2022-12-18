package org.hatulmadan.site.server.application.data.repositories;

import java.util.List;

import org.hatulmadan.site.server.application.data.entities.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleDAO extends CrudRepository<Article, Long> {
	public List<Article> findAllByOrderByIdDesc(); //findAllByOrderByIdAsc();
}
