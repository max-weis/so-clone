package de.maxwell.qa.domain.question;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class QuestionBuilder {

    private Question question;

    public QuestionBuilder() {
        this.question = new Question();
    }

    public QuestionBuilder withUserID(final String userID) {
        notNull(userID, "userId cannot be null");
        this.question.setUserID(userID);
        return this;
    }

    public QuestionBuilder withRating(final Long rating) {
        notNull(rating, "rating cannot be null");
        this.question.setRating(rating);
        return this;
    }

    public QuestionBuilder withTitle(final String title) {
        notNull(title, "title cannot be null");
        notEmpty(title, "title cannot be empty");
        this.question.setTitle(title);
        return this;
    }

    public QuestionBuilder withDescription(final String description) {
        notNull(description, "description cannot be null");
        notEmpty(description, "description cannot be empty");
        this.question.setDescription(description);
        return this;
    }

    public QuestionBuilder withNumberOfAnswers(final Long numberOfAnswers) {
        notNull(numberOfAnswers, "numberOfAnswers cannot be null");
        this.question.setNumberOfAnswers(numberOfAnswers);
        return this;
    }

    public QuestionBuilder withCorrectAnswer(final Long correctAnswer) {
        notNull(correctAnswer, "correctAnswer cannot be null");
        this.question.setCorrectAnswer(correctAnswer);
        return this;
    }

    public QuestionBuilder withViews(final Long views) {
        notNull(views, "views cannot be null");
        this.question.setViews(views);
        return this;
    }

    public QuestionBuilder withCreatedAt(final LocalDateTime createdAt) {
        notNull(createdAt, "createdAt cannot be null");
        this.question.setCreatedAt(createdAt);
        return this;
    }

    public QuestionBuilder withModifiedAt(final LocalDateTime modifiedAt) {
        notNull(modifiedAt, "modifiedAt cannot be null");
        this.question.setModifiedAt(modifiedAt);
        return this;
    }

    public Question build() {
        return this.question;
    }

}
