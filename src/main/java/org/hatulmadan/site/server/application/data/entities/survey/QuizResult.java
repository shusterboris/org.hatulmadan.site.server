package org.hatulmadan.site.server.application.data.entities.survey;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hatulmadan.site.server.application.data.entities.BasicEntity;
@Entity
@Getter
@Setter
@Table(name = "quizresult")
public class QuizResult extends BasicEntity {
	@Column
	private ZonedDateTime start;
	private Long userId;
	private Long quizId;
	private Long answerId;
	
}
