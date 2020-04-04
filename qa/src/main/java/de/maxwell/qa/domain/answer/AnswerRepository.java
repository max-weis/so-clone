package de.maxwell.qa.domain.answer;

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
public class AnswerRepository {
    private static final Logger LOG = LoggerFactory.getLogger(AnswerRepository.class);

    @Inject
    EntityManager em;

    /**
     * Find the answer by id
     *
     * @param id of the answer
     * @return answer
     */
    public Answer findById(final Long id) throws AnswerNotFoundException {
        notNull(id, "id cannot be null");

        LOG.info("Find answer with id {}", id);

        Answer answer = em.find(Answer.class, id);
        if (answer == null) {
            LOG.info("Found no answer with id {}", id);
            throw new AnswerNotFoundException(id);
        }

        return answer;
    }

    /**
     * Find paginated answers
     *
     * @param limit  max number of answer per page
     * @param offset of the page
     * @return list of answers
     */
    public List<Answer> listAllPaginated(final Integer limit, final Integer offset) {
        notNull(limit, "limit cannot be null");
        notNull(offset, "offset cannot be null");

        LOG.info("Find {} answers with offset {}", limit, offset);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Answer> cq = cb.createQuery(Answer.class);

        Root<Answer> root = cq.from(Answer.class);
        cq.select(root);

        TypedQuery<Answer> query = em.createQuery(cq);
        query.setFirstResult(offset * limit);
        query.setMaxResults(limit);

        List<Answer> answers = query.getResultList();

        LOG.info("Found {} answers", answers.size());

        return answers;
    }

    /**
     * Create a new answer
     *
     * @param userID      user who asked the answer
     * @param description of the answer
     * @return
     */
    @Transactional
    public Answer createAnswer(final Long userID, final String description) throws IllegalArgumentException {
        try {
            notNull(userID, "userID cannot be null");
            notNull(description, "description cannot be null");
            notEmpty(description, "description cannot be empty");

            Answer answer = Answer.newBuilder()
                    .withUserID(userID)
                    .withDescription(description)
                    .build();

            em.persist(answer);

            LOG.info("Create answer with id {}", answer.getId());

            return answer;
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("Answer already exists");
        }
    }

    /**
     * update the description of the answer
     *
     * @param id             of the answer
     * @param newDescription
     * @return question
     */
    @Transactional
    public Answer updateDescription(final Long id, final String newDescription) throws AnswerNotFoundException {
        notNull(id, "id cannot be null");
        notNull(newDescription, "new description cannot be null");
        notEmpty(newDescription, "new description cannot be empty");

        Answer answer = em.find(Answer.class, id);
        if (answer == null) {
            LOG.info("Found no answer with id {}", id);
            throw new AnswerNotFoundException(id);
        }

        LOG.info("Update description of question with id {}", id);

        answer.setDescription(newDescription);
        answer.setModifiedAt(LocalDateTime.now());
        em.merge(answer);

        return answer;
    }

    /**
     * update the rating of the answer. Ratings can be negative!
     *
     * @param id     of the answer
     * @param rating new rating of the answer
     * @return new rating
     */
    @Transactional
    public Long updateRating(final Long id, final Integer rating) throws AnswerNotFoundException, IllegalArgumentException {
        notNull(id, "id cannot be null");
        notNull(rating, "rating cannot be null");

        if (rating == 1 || rating == -1 || rating != 0) {
            Answer answer = em.find(Answer.class, id);
            if (answer == null) {
                LOG.info("Found no answer with id {}", id);
                throw new AnswerNotFoundException(id);
            }

            LOG.info("Update rating of answer with id {}", id);

            answer.setRating(answer.getRating() + rating);
            answer.setModifiedAt(LocalDateTime.now());

            em.merge(answer);

            return answer.getRating();
        }
        throw new IllegalArgumentException("rating must be either 1 or -1");
    }

    /**
     * Set the answer as a correct answer of a question
     *
     * @param id      of the answer
     * @param correct correct or not
     * @return new answer
     */
    @Transactional
    public Boolean setCorrectAnswer(final Long id, final Boolean correct) throws AnswerNotFoundException {
        notNull(id, "id cannot be null");
        notNull(correct, "correct cannot be null");

        Answer answer = em.find(Answer.class, id);
        if (answer == null) {
            LOG.info("Found no answer with id {}", id);
            throw new AnswerNotFoundException(id);
        }

        LOG.info("Update correct answer of question with id {}", id);

        answer.setCorrectAnswer(correct);
        answer.setModifiedAt(LocalDateTime.now());

        em.merge(answer);

        return answer.getCorrectAnswer();
    }

    /**
     * Remove a answer with the given id
     *
     * @param id of the answer
     */
    @Transactional
    public void removeAnswer(final Long id) throws AnswerNotFoundException {
        notNull(id, "id cannot be null");

        Answer answer = em.find(Answer.class, id);
        if (answer == null) {
            LOG.info("Found no answer with id {}", id);
            throw new AnswerNotFoundException(id);
        }

        LOG.info("Remove answer with id {}", id);

        em.remove(answer);
    }
}
