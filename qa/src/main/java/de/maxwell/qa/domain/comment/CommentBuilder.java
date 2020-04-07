package de.maxwell.qa.domain.comment;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class CommentBuilder {

    private Comment comment;

    public CommentBuilder() {
        this.comment = new Comment();
    }

    public CommentBuilder withUserID(final String userID) {
        notNull(userID, "userId cannot be null");
        this.comment.setUserID(userID);
        return this;
    }

    public CommentBuilder withRating(final Long rating) {
        notNull(rating, "rating cannot be null");
        this.comment.setRating(rating);
        return this;
    }

    public CommentBuilder withDescription(final String description) {
        notNull(description, "description cannot be null");
        notEmpty(description, "description cannot be empty");
        this.comment.setDescription(description);
        return this;
    }

    public CommentBuilder withCreatedAt(final LocalDateTime createdAt) {
        notNull(createdAt, "createdAt cannot be null");
        this.comment.setCreatedAt(createdAt);
        return this;
    }

    public CommentBuilder withModifiedAt(final LocalDateTime modifiedAt) {
        notNull(modifiedAt, "modifiedAt cannot be null");
        this.comment.setModifiedAt(modifiedAt);
        return this;
    }

    public Comment build() {
        return this.comment;
    }

}
