/*
 * MIT License
 *
 * Copyright (c) 2020 Max Weis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
