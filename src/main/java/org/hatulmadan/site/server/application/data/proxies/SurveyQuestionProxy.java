package org.hatulmadan.site.server.application.data.proxies;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.survey.QuizAnswer;
import org.hatulmadan.site.server.application.data.entities.survey.QuizQuestion;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SurveyQuestionProxy implements Serializable {
    private static final long serialVersionUID = 1000L;
    Long id;
    Long sortOrder;
    String text;
    List<QuizAnswer> answers;

    public SurveyQuestionProxy() { }

    public SurveyQuestionProxy(QuizQuestion q) {
        id = q.getId();
        sortOrder = q.getSortOrder();
        text = q.getText();
    }
}
