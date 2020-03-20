package de.maxwell.qa.domain.question;

import javax.ws.rs.NotFoundException;

public class QuestionNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 1L;

    private Long id;

    public QuestionNotFoundException(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Could not find question with id " + this.id;
    }

}