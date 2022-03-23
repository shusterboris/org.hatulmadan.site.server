package org.hatulmadan.site.server.application.data.proxies;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.courses.Group;
import org.hatulmadan.site.server.application.data.entities.courses.Lesson;
import org.hatulmadan.site.server.application.data.entities.courses.Materials;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class LessonProxy {
    Long id;
    String comment;
    ZonedDateTime start;
    Group group;
    List<Materials> materials;

    public Lesson updateEntity(){
        Lesson l =  new Lesson();
        l.setId(id);
        l.setGroupId(group.getId());
        l.setStart(this.getStart());
        l.setComment(comment);
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

    public LessonProxy(){
        super();
    }

}
