package org.hatulmadan.site.server.application.data.proxies;

import lombok.Getter;
import lombok.Setter;
import org.hatulmadan.site.server.application.data.entities.survey.Quiz;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class SurveyProxy implements Serializable {
    private static final long serialVersionUID = 1000L;
    Long id;
    Long sortOrder;
    String name;
    List<Long> questionIds;
    LocalDateTime startedAt = LocalDateTime.now();
    Integer curPos = 0;
    boolean isDeleted=false;

    public SurveyProxy() {
    }

    public SurveyProxy(Quiz quiz) {
        id = quiz.getId();
        sortOrder = quiz.getSortOrder();
        name = quiz.getName();
    }
    public Quiz updateEntity() {
    	Quiz q=new Quiz();
    	q.setDeleted(isDeleted);
    	if (id !=null )q.setId(id);
    	q.setName(name);
    	q.setSortOrder(sortOrder);
    	return q;
    }
}
