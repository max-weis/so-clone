package de.maxwell.qa.domain.question;

import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDateTime;

public class QuestionBuilder {

    private Question question;

    public QuestionBuilder() {
        this.question = new Question();
    }

    public QuestionBuilder withUserID(final Long userID) {
        Long id = notNull(userID, "userId cannot be null");
        this.question.setUserID(id);
        return this;
    }

    public QuestionBuilder withRating(final Long value) {
        Long rating = notNull(value, "rating cannot be null");
        this.question.setRating(rating);
        return this;
    }

    public QuestionBuilder withTitle(final String title) {
        String t = notNull(title, "title cannot be null");
        this.question.setTitle(t);
        return this;
    }

    public QuestionBuilder withDescription(final String description) {
        String desc = notNull(description, "description cannot be null");
        this.question.setTitle(desc);
        return this;
    }

    public QuestionBuilder withNumberOfAnswers(final Long numberOfAnswers) {
        Long number = notNull(numberOfAnswers, "numberOfAnswers cannot be null");
        this.question.setNumberOfAnswers(number);
        return this;
    }

    public QuestionBuilder withCorrectAnswer(final Long correctAnswer) {
        Long correct = notNull(correctAnswer, "correctAnswer cannot be null");
        this.question.setCorrectAnswer(correct);
        return this;
    }

    public QuestionBuilder withViews(final Long views) {
        Long v = notNull(views, "views cannot be null");
        this.question.setViews(v);
        return this;
    }

    public QuestionBuilder withCreatedAt(final LocalDateTime createdAt) {
        LocalDateTime created = notNull(createdAt, "createdAt cannot be null");
        this.question.setCreatedAt(created);
        return this;
    }

    public QuestionBuilder withModifiedAt(final LocalDateTime modifiedAt) {
        LocalDateTime modified = notNull(modifiedAt, "modifiedAt cannot be null");
        this.question.setModifiedAt(modified);
        return this;
    }

    public Question build() {
        return this.question;
    }

}