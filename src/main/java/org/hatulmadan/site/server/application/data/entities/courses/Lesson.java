package org.hatulmadan.site.server.application.data.entities.courses;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import org.hatulmadan.site.server.application.data.entities.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "LESSONS")
public class Lesson extends BasicEntity {
	@Column
	private ZonedDateTime start;
	@Column
	private Long groupId;
	@Column
	private String comment;
}
