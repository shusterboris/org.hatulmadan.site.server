package org.hatulmadan.site.server.application.data.entities.survey;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.BasicEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class QuizQuestion extends BasicEntity {
    private String text;
    @ManyToOne
    private Quiz quiz;
}
