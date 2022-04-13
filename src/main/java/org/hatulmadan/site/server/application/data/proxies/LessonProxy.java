package org.hatulmadan.site.server.application.data.proxies;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.entities.courses.Lesson;
import org.hatulmadan.site.server.application.data.entities.courses.Materials;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LessonProxy {
    Long id;
    String comment;
    ZonedDateTime start;
    Group group;
    List<Materials> materials;
    Long sortOrder = 0L;

    public Lesson updateEntity(){
        Lesson l =  new Lesson();
        l.setId(id);
        l.setGroupId(group!=null ? group.getId() : null);
        l.setStart(this.getStart());
        l.setComment(comment);
        l.setSortOrder(sortOrder);
        l.setGroupName(group!=null ? group.getName() : "");
        return l;
    }

    public void init(Lesson l){
        this.id = l.getId();
        this.comment = l.getComment();

    }

    public LessonProxy(Long id, String comment, ZonedDateTime start, Group group, List<Materials> materials) {
        this.id = id;
        this.comment = comment;
        this.start = start;
        this.group = group;
        this.materials = materials;
    }

    public LessonProxy(Lesson lesson) {
        this(lesson.getId(), lesson.getComment(), lesson.getStart(), null, new ArrayList<Materials>());
        setSortOrder(lesson.getSortOrder());
    }

    public LessonProxy(){
        super();
    }

}
