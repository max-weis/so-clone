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

package de.maxwell.qa.domain.answer;

import de.maxwell.qa.infrastructure.stereotype.Repository;
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
     * Find paginated answers by questionID
     *
     * @param questionID id of the question
     * @param limit  max number of answer per page
     * @param offset of the page
     * @return list of answers
     */
    public List<Answer> listAllPaginatedByQuestionID(final Long questionID, final Integer limit, final Integer offset) {
        notNull(limit, "limit cannot be null");
        notNull(offset, "offset cannot be null");

        LOG.info("Find {} answers with offset {}", limit, offset);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Answer> cq = cb.createQuery(Answer.class);

        Root<Answer> root = cq.from(Answer.class);
        cq.select(root);
        cq.where(cb.equal(root.get("questionID"), questionID));

        TypedQuery<Answer> query = em.createQuery(cq);
        query.setFirstResult(offset * limit);
        query.setMaxResults(limit);

        List<Answer> answers = query.getResultList();

        LOG.info("Found {} answers", answers.size());

        return answers;
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
     * Find all answers of a question
     *

     * @return list of answers
     */
    public List<Answer> listAllAnswers(final Long questionID) {
        notNull(questionID, "questionID cannot be null");

        LOG.info("Find all answers of question with id: {}", questionID);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Answer> cq = cb.createQuery(Answer.class);

        Root<Answer> root = cq.from(Answer.class);
        cq.select(root);
        cq.where(cb.equal(root.get("questionID"), questionID));

        List<Answer> list = em.createQuery(cq)
                .getResultList();

        LOG.info("Found {} answers", list.size());

        return list;
    }

    /**
     * Create a new answer
     *
     * @param userID      user who asked the answer
     * @param description of the answer
     * @return
     */
    @Transactional
    public Answer createAnswer(final String userID, final Long questionID, final String description) throws IllegalArgumentException {
        try {
            notNull(userID, "userID cannot be null");
            notNull(questionID, "questionID cannot be null");
            notNull(description, "description cannot be null");
            notEmpty(description, "description cannot be empty");

            Answer answer = Answer.newBuilder()
                    .withUserID(userID)
                    .withQuestionID(questionID)
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
     * @return answer
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
     * Counts the number of answers belonging to the given user
     *
     * @param userID given user
     * @return number of answers
     */
    public Long countNumberOfAnswersOfUser(final String userID) {
        LOG.info("Find answers of user: {}", userID);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Answer> root = cq.from(Answer.class);
        cq.select(cb.count(root));
        cq.where(cb.equal(root.get("userID"), userID));

        Long count = em.createQuery(cq)
                .getSingleResult();

        LOG.info("Found {} answers of user with id: {}", count, userID);

        return count;
    }

    /**
     * Counts the number of answers belonging to the given question
     *
     * @param questionID given question
     * @return number of answers
     */
    public Long countNumberOfAnswersOfQuestion(final Long questionID) {
        LOG.info("Find answers of question: {}", questionID);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Answer> root = cq.from(Answer.class);
        cq.select(cb.count(root));
        cq.where(cb.equal(root.get("questionID"), questionID));

        Long count = em.createQuery(cq)
                .getSingleResult();

        LOG.info("Found {} answers of question with id: {}", count, questionID);

        return count;
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
