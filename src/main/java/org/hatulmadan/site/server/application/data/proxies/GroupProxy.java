package org.hatulmadan.site.server.application.data.proxies;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.entities.security.User;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GroupProxy {
    private Long id;
    private String name;
    private String schedule;
    private long course;
    private String courseName;
    private List<User> users = new ArrayList<>();
    private ZonedDateTime startCourceDate;//начало оплачиваемого периода
    private ZonedDateTime endCourceDate;//конец оплачиваемого периода
    private float price;
    public GroupProxy(Group entity){
        id = entity.getId();
        name = entity.getName();
        schedule = entity.getSchedule();
        course = entity.getCourse();
        courseName = entity.getCourseName();
        users.addAll(entity.getUsers());
    }
	public GroupProxy(Long id2, String name2) {
		id=id2;
		name=name2;
	}
}
