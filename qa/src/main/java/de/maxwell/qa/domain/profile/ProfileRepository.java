package de.maxwell.qa.domain.profile;

import de.maxwell.qa.infrastructure.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class ProfileRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileRepository.class);

    @Inject
    EntityManager em;

    /**
     * Find the profile by id
     *
     * @param id of the profile
     * @return profile
     */
    public Profile findById(final Long id) throws ProfileNotFoundException {
        notNull(id, "id cannot be null");

        LOG.info("Find answer with id {}", id);

        Profile profile = em.find(Profile.class, id);
        if (profile == null) {
            LOG.info("Found no answer with id {}", id);
            throw new ProfileNotFoundException(id);
        }

        return profile;
    }

    /**
     * Find paginated profiles
     *
     * @param limit  max number of profile per page
     * @param offset of the page
     * @return list of profiles
     */
    public List<Profile> listAllPaginated(final Integer limit, final Integer offset) {
        notNull(limit, "limit cannot be null");
        notNull(offset, "offset cannot be null");

        LOG.info("Find {} profiles with offset {}", limit, offset);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Profile> cq = cb.createQuery(Profile.class);

        Root<Profile> root = cq.from(Profile.class);
        cq.select(root);

        TypedQuery<Profile> query = em.createQuery(cq);
        query.setFirstResult(offset * limit);
        query.setMaxResults(limit);

        List<Profile> profiles = query.getResultList();

        LOG.info("Found {} profiles", profiles.size());

        return profiles;
    }

    /**
     * Create a new profile
     *
     * @param userID user who created the profile
     * @param firstName
     * @return
     */
    @Transactional
    public Profile createProfile(final Long userID, String firstName) throws IllegalArgumentException {
        try {
            notNull(userID, "userID cannot be null");
            notNull(firstName, "firstName cannot be null");
            notEmpty(firstName, "firstName cannot be empty");

            Profile profile = Profile.newBuilder()
                    .withUserID(userID)
                    .withFirstName(firstName)
                    .build();

            em.persist(profile);

            LOG.info("Create profile with id {}", profile.getId());

            return profile;
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("Profile already exists");
        }
    }

    /**
     * update the first name of the profile
     *
     * @param id           of the profile
     * @param newFirstName
     * @return profile
     */
    @Transactional
    public Profile updateFirstName(final Long id, final String newFirstName) throws ProfileNotFoundException {
        notNull(id, "id cannot be null");
        notNull(newFirstName, "new first name cannot be null");
        notEmpty(newFirstName, "new first name  cannot be empty");

        Profile profile = em.find(Profile.class, id);
        if (profile == null) {
            LOG.info("Found no profile with id {}", id);
            throw new ProfileNotFoundException(id);
        }

        LOG.info("Update first name of profile with id {}", id);

        profile.setFirstName(newFirstName);
        profile.setModifiedAt(LocalDateTime.now());
        em.merge(profile);

        return profile;
    }

    /**
     * update the last name of the profile
     *
     * @param id          of the profile
     * @param newLastName
     * @return profile
     */
    @Transactional
    public Profile updateLastName(final Long id, final String newLastName) throws ProfileNotFoundException {
        notNull(id, "id cannot be null");
        notNull(newLastName, "new last name cannot be null");
        notEmpty(newLastName, "new last name  cannot be empty");

        Profile profile = em.find(Profile.class, id);
        if (profile == null) {
            LOG.info("Found no profile with id {}", id);
            throw new ProfileNotFoundException(id);
        }

        LOG.info("Update last name of profile with id {}", id);

        profile.setLastName(newLastName);
        profile.setModifiedAt(LocalDateTime.now());
        em.merge(profile);

        return profile;
    }

    /**
     * update the description of the profile
     *
     * @param id             of the profile
     * @param newDescription
     * @return profile
     */
    @Transactional
    public Profile updateDescription(final Long id, final String newDescription) throws ProfileNotFoundException {
        notNull(id, "id cannot be null");
        notNull(newDescription, "new description cannot be null");
        notEmpty(newDescription, "new description cannot be empty");

        Profile profile = em.find(Profile.class, id);
        if (profile == null) {
            LOG.info("Found no profile with id {}", id);
            throw new ProfileNotFoundException(id);
        }

        LOG.info("Update description of profile with id {}", id);

        profile.setDescription(newDescription);
        profile.setModifiedAt(LocalDateTime.now());
        em.merge(profile);

        return profile;
    }

    /**
     * update the image of the profile
     *
     * @param id    of the profile
     * @param image new image of the profile
     * @return new reputation
     */
    @Transactional
    public void updateImage(final Long id, final Byte[] image) throws ProfileNotFoundException {
        notNull(id, "id cannot be null");
        notNull(image, "new image cannot be null");

        Profile profile = em.find(Profile.class, id);
        if (profile == null) {
            LOG.info("Found no profile with id {}", id);
            throw new ProfileNotFoundException(id);
        }

        LOG.info("Update image of profile with id {}", id);

        profile.setImage(image);
        profile.setModifiedAt(LocalDateTime.now());
        em.merge(profile);
    }

    /**
     * update the reputation of the profile
     *
     * @param id         of the profile
     * @param reputation new reputation of the profile
     * @return new reputation
     */
    @Transactional
    public Long updateReputation(final Long id, final Integer reputation) throws ProfileNotFoundException {
        notNull(id, "id cannot be null");
        notNull(reputation, "reputation cannot be null");

        Profile profile = em.find(Profile.class, id);
        if (profile == null) {
            LOG.info("Found no profile with id {}", id);
            throw new ProfileNotFoundException(id);
        }

        LOG.info("Update reputation of profile with id {}", id);

        profile.setReputation(profile.getReputation() + reputation);
        profile.setModifiedAt(LocalDateTime.now());

        em.merge(profile);

        return profile.getReputation();
    }

    /**
     * Remove a profile with the given id
     *
     * @param id of the profile
     */
    @Transactional
    public void removeProfile(final Long id) throws ProfileNotFoundException {
        notNull(id, "id cannot be null");

        Profile profile = em.find(Profile.class, id);
        if (profile == null) {
            LOG.info("Found no profile with id {}", id);
            throw new ProfileNotFoundException(id);
        }

        LOG.info("Remove profile with id {}", id);

        em.remove(profile);
    }
}
