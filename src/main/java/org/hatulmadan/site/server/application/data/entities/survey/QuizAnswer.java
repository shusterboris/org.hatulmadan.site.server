package org.hatulmadan.site.server.application.data.entities.survey;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.BasicEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class QuizAnswer extends BasicEntity {
    private String text;
    private boolean right = false;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuizQuestion question;
}
