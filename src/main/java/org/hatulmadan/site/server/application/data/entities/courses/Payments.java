package org.hatulmadan.site.server.application.data.entities.courses;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hatulmadan.site.server.application.data.entities.BasicEntity;
@Getter
@Setter
@Entity
public class Payments extends BasicEntity {
	@Column
	private ZonedDateTime pdate;
	@Column
	private Long userId;
	@Column
	private Double psum;
	@Column
	private String comment;
		// учебная группа
	@Column
	private Long groupId;
	//занятие от которого отсчитываем начало оплаты
	@Column
	private ZonedDateTime startCourceDate;
	//конец оплачиваемого периода (будем вычислять)
	@Column
	private ZonedDateTime lastCourceDate;
}
