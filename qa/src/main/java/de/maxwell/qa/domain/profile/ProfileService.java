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

import de.maxwell.qa.infrastructure.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ProfileService {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileService.class);

    @Inject
    ProfileRepository profileRepository;

    public Profile findComment(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("Find profile by id: {}", id);

        return this.profileRepository.findById(id);
    }

    public List<Profile> listProfiles(final Integer limit, final Integer offset) {
        notNull(limit, "limit cannot be null");
        notNull(offset, "offset cannot be null");

        LOG.info("Find {} profile by question id", limit * offset);

        return this.profileRepository.listAllPaginated(limit, offset);
    }

    public Profile createProfile(final String userID, final String firstName, final String lastName) {
        notNull(userID, "userID cannot be null");
        notNull(firstName, "firstName cannot be null");
        notNull(lastName, "lastName cannot be null");

        notEmpty(firstName, "firstName cannot be empty");
        notEmpty(lastName, "lastName cannot be empty");

        LOG.info("Create new profile");

        return this.profileRepository.createProfile(userID, firstName, lastName);
    }

    public Profile updateFirstName(final Long id, final String firstName) {
        notNull(id, "userID cannot be null");
        notNull(firstName, "firstName cannot be null");

        notEmpty(firstName, "firstName cannot be empty");

        LOG.info("Update firstName of profile with id: {}", id);

        return this.profileRepository.updateFirstName(id, firstName);
    }

    public Profile updateLastName(final Long id, final String lastName) {
        notNull(id, "userID cannot be null");
        notNull(lastName, "lastName cannot be null");

        notEmpty(lastName, "lastName cannot be empty");

        LOG.info("Update lastName of profile with id: {}", id);

        return this.profileRepository.updateLastName(id, lastName);
    }

    public Profile updateDescription(final Long id, final String description) {
        notNull(id, "userID cannot be null");
        notNull(description, "description cannot be null");

        notEmpty(description, "description cannot be empty");

        LOG.info("Update description of profile with id: {}", id);

        return this.profileRepository.updateDescription(id, description);
    }

    public Long updateReputation(final Long id, final Integer reputation) {
        notNull(id, "userID cannot be null");
        notNull(reputation, "reputation cannot be null");

        LOG.info("Update reputation of profile with id: {}", id);

        return this.profileRepository.updateReputation(id, reputation);
    }

    public void removeProfile(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("Remove Profile with id: {}", id);

        this.profileRepository.removeProfile(id);
    }
}
