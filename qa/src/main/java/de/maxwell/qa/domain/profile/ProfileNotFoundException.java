package de.maxwell.qa.domain.profile;

import javax.ws.rs.NotFoundException;

public class ProfileNotFoundException extends NotFoundException {
    private static final long serialVersionUID = 1L;

    private Long id;

    public ProfileNotFoundException(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Could not find profile with id " + this.id;
    }
}
