 package org.hatulmadan.site.server.application.data.proxies;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class QuizAnswerProxy {
	private ZonedDateTime start;
	private Long userId;
	private String userName;
	private Long quizId;
	private String quizName;
	private Long questionValue;
	private Long answerId;
	private String answerValue;
}
