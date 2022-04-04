package org.hatulmadan.site.server.application.data.entities.courses;

import org.hatulmadan.site.server.application.data.entities.BasicEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
public class Materials extends BasicEntity {
    private String comment;
    private Long lesson;
    private String youtubeLink;
    private String fileLink;
    private String srvFileLink;
}
