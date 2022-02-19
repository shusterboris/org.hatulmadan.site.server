package org.hatulmadan.site.server.application.data.entities.survey;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.BasicEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Quiz extends BasicEntity {
    private String name;
}
