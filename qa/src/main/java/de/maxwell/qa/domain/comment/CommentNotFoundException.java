package de.maxwell.qa.domain.comment;

import javax.ws.rs.NotFoundException;

public class CommentNotFoundException  extends NotFoundException {
    private static final long serialVersionUID = 1L;

    private Long id;

    public CommentNotFoundException(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Could not find comment with id " + this.id;
    }

}
