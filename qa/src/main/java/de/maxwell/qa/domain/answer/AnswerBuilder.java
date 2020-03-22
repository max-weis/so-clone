package de.maxwell.qa.domain.answer;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class AnswerBuilder {

    private Answer answer;

    public AnswerBuilder() {
        answer = new Answer();
    }

    public AnswerBuilder withUserID(final Long userID) {
        notNull(userID, "userId cannot be null");
        this.answer.setUserID(userID);
        return this;
    }

    public AnswerBuilder withRating(final Long rating) {
        notNull(rating, "rating cannot be null");
        this.answer.setRating(rating);
        return this;
    }

    public AnswerBuilder withDescription(final String description) {
        notNull(description, "description cannot be null");
        notEmpty(description, "description cannot be empty");
        this.answer.setDescription(description);
        return this;
    }

    public AnswerBuilder withCorrectAnswer(final Boolean correct) {
        notNull(correct, "correct cannot be null");
        this.answer.setCorrectAnswer(correct);
        return this;
    }

    public AnswerBuilder withCreatedAt(final LocalDateTime createdAt) {
        notNull(createdAt, "createdAt cannot be null");
        this.answer.setCreatedAt(createdAt);
        return this;
    }

    public AnswerBuilder withModifiedAt(final LocalDateTime modifiedAt) {
        notNull(modifiedAt, "modifiedAt cannot be null");
        this.answer.setModifiedAt(modifiedAt);
        return this;
    }

    public Answer build() {
        return this.answer;
    }
}
