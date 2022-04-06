package org.hatulmadan.site.server.application.data.entities.courses;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hatulmadan.site.server.application.data.entities.BasicEntity;

@Entity
@Getter
@Setter
@Table(name = "usergroups")
public class Group extends BasicEntity {
    @Column(name = "groupName")
    private String name;
    private String schedule;
    private long course;
    private String courseName;
}
