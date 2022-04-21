package org.hatulmadan.site.server.application.data.entities.courses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.hatulmadan.site.server.application.data.entities.BasicEntity;
import org.hatulmadan.site.server.application.data.entities.security.User;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "usergroups")
public class Group extends BasicEntity implements Serializable {
    @Column(name = "groupName", unique = true)
    private String name;
    private String schedule;
    private long course;
    private String courseName;
    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private List<User> users;
}
