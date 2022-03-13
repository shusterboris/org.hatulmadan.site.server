package org.hatulmadan.site.server.application.data.entities.courses;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;

import org.hatulmadan.site.server.application.data.entities.BasicEntity;

@Entity
@Getter
@Setter

public class CourseName extends BasicEntity {
	private String name;
}
