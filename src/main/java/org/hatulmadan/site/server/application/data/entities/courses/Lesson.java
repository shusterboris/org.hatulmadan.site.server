package org.hatulmadan.site.server.application.data.entities.courses;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import org.hatulmadan.site.server.application.data.entities.BasicEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
public class Lesson extends BasicEntity {
	private ZonedDateTime start;
	private Long group;
	private int number;
}
