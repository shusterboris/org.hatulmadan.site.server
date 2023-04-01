package org.hatulmadan.site.server.application.data.repositories;

import java.util.List;

import org.hatulmadan.site.server.application.data.entities.courses.Payments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PaymentDAO extends CrudRepository<Payments, Long> {
	List<Payments> findByUserId(Long userId);
	
	@Query("select p, g.name from Payments p, Group g where p.groupId = g.id and p.userId=?1")
	List<Object[]> findByUserIdWithGroup(Long userId);
	@Query("select sum(p.psum) from Payments p where p.groupId = ?1 and p.userId=?2")
	double sumByGroupIdAndUserId(Long groupId, Long userId);
	
	@Query("select count(p.psum) from Payments p where p.groupId = ?1 and p.userId=?2")
	int countByGroupIdAndUserId(Long groupId, Long userId);
}

