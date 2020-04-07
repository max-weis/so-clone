package de.maxwell.qa.domain.question;

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
public class QuestionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionRepository.class);

    @Inject
    EntityManager em;

    /**
     * Find the question by id
     *
     * @param id of the question
     * @return question
     */
    public Question findById(final Long id) throws QuestionNotFoundException {
        notNull(id, "id cannot be null");

        LOG.info("Find question with id {}", id);

        Question question = em.find(Question.class, id);
        if (question == null) {
            LOG.info("Found no question with id {}", id);
            throw new QuestionNotFoundException(id);
        }

        return question;
    }

    /**
     * Find paginated questions
     *
     * @param limit  max number of question per page
     * @param offset of the page
     * @return list of questions
     */
    public List<Question> listAllPaginated(final Integer limit, final Integer offset) {
        notNull(limit, "limit cannot be null");
        notNull(offset, "offset cannot be null");

        LOG.info("Find {} questions with offset {}", limit, offset);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Question> cq = cb.createQuery(Question.class);

        Root<Question> root = cq.from(Question.class);
        cq.select(root);

        TypedQuery<Question> query = em.createQuery(cq);
        query.setFirstResult(offset * limit);
        query.setMaxResults(limit);

        List<Question> questions = query.getResultList();

        LOG.info("Found {} questions", questions.size());

        return questions;
    }

    /**
     * Create a new question
     *
     * @param userID      user who asked the question
     * @param title       of the question
     * @param description of the question
     * @return
     */
    @Transactional
    public Question createQuestion(final String userID, final String title, final String description) throws IllegalArgumentException {
        try {
            notNull(userID, "userID cannot be null");
            notNull(title, "title cannot be null");
            notNull(description, "description cannot be null");
            notEmpty(title, "title cannot be empty");
            notEmpty(description, "description cannot be empty");

            Question question = Question.newBuilder()
                    .withUserID(userID)
                    .withTitle(title)
                    .withDescription(description)
                    .build();

            em.persist(question);

            LOG.info("Create question with id {}", question.getId());

            return question;
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("Question already exists");
        }
    }

    /**
     * Update the title of the question
     *
     * @param id       of the question
     * @param newTitle
     * @return question
     */
    @Transactional
    public Question updateTitle(final Long id, final String newTitle) throws QuestionNotFoundException {
        notNull(id, "id cannot be null");
        notNull(newTitle, "new title cannot be null");
        notEmpty(newTitle, "new title cannot be empty");

        Question question = em.find(Question.class, id);
        if (question == null) {
            LOG.info("Found no question with id {}", id);
            throw new QuestionNotFoundException(id);
        }

        LOG.info("Update title of question with id {}", id);

        question.setTitle(newTitle);
        question.setModifiedAt(LocalDateTime.now());
        em.merge(question);

        return question;
    }

    /**
     * update the description of the question
     *
     * @param id             of the question
     * @param newDescription
     * @return question
     */
    @Transactional
    public Question updateDescription(final Long id, final String newDescription) throws QuestionNotFoundException {
        notNull(id, "id cannot be null");
        notNull(newDescription, "new description cannot be null");
        notEmpty(newDescription, "new description cannot be empty");

        Question question = em.find(Question.class, id);
        if (question == null) {
            LOG.info("Found no question with id {}", id);
            throw new QuestionNotFoundException(id);
        }

        LOG.info("Update description of question with id {}", id);

        question.setDescription(newDescription);
        question.setModifiedAt(LocalDateTime.now());
        em.merge(question);

        return question;
    }

    /**
     * Increment the view of the question
     *
     * @param id of the question
     * @return new view counter
     */
    @Transactional
    public Long incrementView(final Long id) throws QuestionNotFoundException {
        notNull(id, "id cannot be null");

        Question question = em.find(Question.class, id);
        if (question == null) {
            LOG.info("Found no question with id {}", id);
            throw new QuestionNotFoundException(id);
        }

        LOG.info("Increment view of question with id {}", id);

        question.setViews(question.getViews() + 1L);
        question.setModifiedAt(LocalDateTime.now());

        em.merge(question);

        return question.getViews();
    }

    /**
     * update the rating of the question. Rating can be negative!
     *
     * @param id     of the question
     * @param rating new rating of the question
     * @return new rating
     */
    @Transactional
    public Long updateRating(final Long id, final Integer rating) throws QuestionNotFoundException, IllegalArgumentException {
        notNull(id, "id cannot be null");
        notNull(rating, "rating cannot be null");

        if (rating == 1 || rating == -1 || rating != 0) {
            Question question = em.find(Question.class, id);
            if (question == null) {
                LOG.info("Found no question with id {}", id);
                throw new QuestionNotFoundException(id);
            }

            LOG.info("Update rating of question with id {}", id);

            question.setRating(question.getRating() + rating);
            question.setModifiedAt(LocalDateTime.now());

            em.merge(question);

            return question.getRating();
        }
        throw new IllegalArgumentException("rating must be either 1 or -1");
    }

    /**
     * Set a correct answer id of the question
     *
     * @param id       of the question
     * @param answerID of the correct answer
     * @return new answer
     */
    @Transactional
    public Long setCorrectAnswer(final Long id, final Long answerID) throws QuestionNotFoundException {
        notNull(id, "id cannot be null");
        notNull(answerID, "answerID cannot be null");

        Question question = em.find(Question.class, id);
        if (question == null) {
            LOG.info("Found no question with id {}", id);
            throw new QuestionNotFoundException(id);
        }

        LOG.info("Update correct answer of question with id {}", id);

        question.setCorrectAnswer(answerID);
        question.setModifiedAt(LocalDateTime.now());

        em.merge(question);

        return question.getCorrectAnswer();
    }

    /**
     * Counts the number of questions belonging to the given user
     *
     * @param userID given user
     * @return number of questions
     */
    public Long countNumberOfQuestionsOfUser(final String userID) {
        LOG.info("Find questions of user: {}", userID);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Question> root = cq.from(Question.class);
        cq.select(cb.count(root));
        cq.where(cb.equal(root.get("userID"), userID));

        Long count = em.createQuery(cq)
                .getSingleResult();

        LOG.info("Found {} questions of user with id: {}", count, userID);

        return count;
    }

    /**
     * Remove a question with the given id
     *
     * @param id of the question
     */
    @Transactional
    public void removeQuestion(final Long id) throws QuestionNotFoundException {
        notNull(id, "id cannot be null");

        Question question = em.find(Question.class, id);
        if (question == null) {
            LOG.info("Found no question with id {}", id);
            throw new QuestionNotFoundException(id);
        }

        LOG.info("Remove question with id {}", id);

        em.remove(question);
    }
}
