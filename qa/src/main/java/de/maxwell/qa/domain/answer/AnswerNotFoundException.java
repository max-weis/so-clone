package de.maxwell.qa.domain.answer;

import javax.ws.rs.NotFoundException;

public class AnswerNotFoundException  extends NotFoundException {
    private static final long serialVersionUID = 1L;

    private Long id;

    public AnswerNotFoundException(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Could not find answer with id " + this.id;
    }

}
