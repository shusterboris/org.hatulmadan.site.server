package org.hatulmadan.site.server.application.data.proxies;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.entities.security.User;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GroupProxy {
    private String name;
    private String schedule;
    private long course;
    private String courseName;
    private List<User> users = new ArrayList<>();

    public GroupProxy(Group entity){
        name = entity.getName();
        schedule = entity.getSchedule();
        course = entity.getCourse();
        courseName = entity.getCourseName();
        users.addAll(entity.getUsers());
    }
}
