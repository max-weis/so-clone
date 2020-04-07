package de.maxwell.qa.domain.profile;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class ProfileBuilder {
    private Profile profile;

    public ProfileBuilder() {
        this.profile = new Profile();
    }

    public ProfileBuilder withUserID(final String userID) {
        notNull(userID, "userId cannot be null");
        this.profile.setUserID(userID);
        return this;
    }

    public ProfileBuilder withReputation(final Long reputation) {
        notNull(reputation, "reputation cannot be null");
        this.profile.setReputation(reputation);
        return this;
    }

    public ProfileBuilder withImage(final Byte[] img) {
        notNull(img, "img cannot be null");
        this.profile.setImage(img);
        return this;
    }

    public ProfileBuilder withFirstName(final String firstName) {
        notNull(firstName, "firstName cannot be null");
        notEmpty(firstName, "firstName cannot be empty");
        this.profile.setFirstName(firstName);
        return this;
    }

    public ProfileBuilder withLastName(final String lastName) {
        notNull(lastName, "lastName cannot be null");
        notEmpty(lastName, "lastName cannot be empty");
        this.profile.setLastName(lastName);
        return this;
    }

    public ProfileBuilder withDescription(final String description) {
        notNull(description, "description cannot be null");
        notEmpty(description, "description cannot be empty");
        this.profile.setDescription(description);
        return this;
    }

    public ProfileBuilder withCreatedAt(final LocalDateTime createdAt) {
        notNull(createdAt, "createdAt cannot be null");
        this.profile.setCreatedAt(createdAt);
        return this;
    }

    public ProfileBuilder withModifiedAt(final LocalDateTime modifiedAt) {
        notNull(modifiedAt, "modifiedAt cannot be null");
        this.profile.setModifiedAt(modifiedAt);
        return this;
    }

    public Profile build() {
        return this.profile;
    }
}
