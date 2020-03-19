package de.maxwell.qa.domain.question;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.slf4j.LoggerFactory;

import de.maxwell.qa.infrastructure.repository.Repository;

import static org.apache.commons.lang3.Validate.notNull;

import java.time.LocalDateTime;
import org.slf4j.Logger;

@Repository
public class QuestionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionRepository.class);

    @Inject
    private EntityManager em;

    /**
     * Find the question by id
     * 
     * @param id of the question
     * @return question
     */
    public Question findById(final Long id) {
        notNull(id, "id cannot be null");

        LOG.debug("Find question with id {}", id);

        Question question = em.find(Question.class, id);
        if (question == null) {
            throw new QuestionNotFoundException(id);
        }

        return question;
    }

    /**
     * Create a new question
     * 
     * @param userID      user who asked the question
     * @param title       of the question
     * @param description of the question
     * @return
     */
    public Question createQuestion(final Long userID, final String title, final String description) {
        try {
            LocalDateTime now = LocalDateTime.now();
            Question question = Question.newBuilder().withUserID(userID).withRating(0L).withTitle(title)
                    .withDescription(description).withNumberOfAnswers(0L).withViews(0L).withCreatedAt(now)
                    .withModifiedAt(now).build();

            em.persist(question);

            LOG.debug("Create question with id {}", question.getId());

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
    public Question updateTitle(final Long id, final String newTitle) {
        notNull(id, "id cannot be null");
        notNull(newTitle, "new title cannot be null");

        Question question = em.find(Question.class, id);
        if (question == null) {
            throw new QuestionNotFoundException(id);
        }

        LOG.debug("Update title of question with id {}", id);

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
    public Question updateDescription(final Long id, final String newDescription) {
        notNull(id, "id cannot be null");
        notNull(newDescription, "new description cannot be null");

        Question question = em.find(Question.class, id);
        if (question == null) {
            throw new QuestionNotFoundException(id);
        }

        LOG.debug("Update description of question with id {}", id);

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
    public Long incrementView(final Long id) {
        notNull(id, "id cannot be null");

        Question question = em.find(Question.class, id);
        if (question == null) {
            throw new QuestionNotFoundException(id);
        }

        LOG.debug("Increment view of question with id {}", id);

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
    public Long updateRating(final Long id, final Long rating) {
        notNull(id, "id cannot be null");
        notNull(rating, "rating cannot be null");

        if (id != 1 || id != -1) {
            throw new IllegalAccessError("rating must be either 1 or -1");
        }

        Question question = em.find(Question.class, id);
        if (question == null) {
            throw new QuestionNotFoundException(id);
        }

        LOG.debug("Update rating of question with id {}", id);

        question.setRating(question.getRating() + rating);
        question.setModifiedAt(LocalDateTime.now());

        em.merge(question);

        return question.getRating();
    }

    /**
     * Set a correct answer id of the question
     * 
     * @param id       of the question
     * @param answerID of the correct answer
     * @return new answer
     */
    public Long setCorrectAnswer(final Long id, final Long answerID) {
        notNull(id, "id cannot be null");
        notNull(answerID, "answerID cannot be null");

        Question question = em.find(Question.class, id);
        if (question == null) {
            throw new QuestionNotFoundException(id);
        }

        LOG.debug("Update correct answer of question with id {}", id);

        question.setCorrectAnswer(answerID);
        question.setModifiedAt(LocalDateTime.now());

        em.merge(question);

        return question.getCorrectAnswer();
    }

    public void removeQuestion(final Long id) {
        notNull(id, "id cannot be null");

        Question question = em.find(Question.class, id);
        if (question == null) {
            throw new QuestionNotFoundException(id);
        }

        LOG.debug("Remove question with id {}", id);

        em.remove(question);
    }
}