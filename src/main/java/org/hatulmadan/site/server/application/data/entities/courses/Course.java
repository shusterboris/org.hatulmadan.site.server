package org.hatulmadan.site.server.application.data.entities.courses;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hatulmadan.site.server.application.data.entities.BasicEntity;

@Entity
@Getter
@Setter

@Table(name="course", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
public class Course extends BasicEntity {
	private String name;
}
