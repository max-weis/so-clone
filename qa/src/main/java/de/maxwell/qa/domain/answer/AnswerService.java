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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class AnswerService {
    private static final Logger LOG = LoggerFactory.getLogger(AnswerService.class);

    @Inject
    AnswerRepository answerRepository;

    public Answer findAnswer(final Long id) throws AnswerNotFoundException, NullPointerException {
        notNull(id, "id cannot be null");

        LOG.info("Find answer by id: {}", id);

        return this.answerRepository.findById(id);
    }

    public List<Answer> findAnswers(final Integer limit, final Integer offset) throws NullPointerException {
        notNull(limit, "limit cannot be null");
        notNull(offset, "offset cannot be null");

        LOG.info("Find {} questions", limit * offset);

        return this.answerRepository.listAllPaginated(limit, offset);
    }

    public List<Answer> findAnswersByQuestionID(final Long questionID, final Integer limit, final Integer offset) throws NullPointerException {
        notNull(questionID, "questionID cannot be null");
        notNull(limit, "limit cannot be null");
        notNull(offset, "offset cannot be null");

        LOG.info("Find {} questions", limit * offset);

        return this.answerRepository.listAllPaginatedByQuestionID(questionID, limit, offset);
    }

    public Answer createAnswer(final String userID, final Long questionID, final String description) throws IllegalArgumentException, NullPointerException {
        notNull(userID, "userID cannot be null");
        notNull(questionID, "questionID cannot be null");
        notNull(description, "description cannot be null");

        notEmpty(description, "description cannot be empty");

        LOG.info("Create new answer");

        return this.answerRepository.createAnswer(userID, questionID, description);
    }

    public Answer updateDescription(final Long id, final String newDescription) throws AnswerNotFoundException, NullPointerException {
        notNull(id, "id cannot be null");
        notNull(newDescription, "newDescription cannot be null");

        notEmpty(newDescription, "newDescription cannot be empty");

        LOG.info("Update description of answer with id: {}", id);

        return this.answerRepository.updateDescription(id, newDescription);
    }

    public Long incrementRating(final Long id) throws AnswerNotFoundException, NullPointerException {
        notNull(id, "id cannot be null");

        LOG.info("Increment rating of answer with id: {}", id);

        return this.answerRepository.updateRating(id, 1);
    }

    public Long decrementRating(final Long id) throws AnswerNotFoundException, NullPointerException {
        notNull(id, "id cannot be null");

        LOG.info("Decrement rating of answer with id: {}", id);

        return this.answerRepository.updateRating(id, -1);
    }

    public boolean setCorrectAnswer(final Long id) throws AnswerNotFoundException, NullPointerException {
        notNull(id, "id cannot be null");

        LOG.info("Set correct answer of id: {}", id);

        return this.answerRepository.setCorrectAnswer(id, true);
    }

    public boolean unsetCorrectAnswer(final Long id) throws AnswerNotFoundException, NullPointerException {
        notNull(id, "id cannot be null");

        LOG.info("Unset correct answer of id: {}", id);

        return this.answerRepository.setCorrectAnswer(id, false);
    }

    public Long countNumberOfAnswersOfUser(final String userID) throws NullPointerException, IllegalArgumentException {
        notNull(userID, "userID cannot be null");
        notEmpty(userID, "userID cannot be empty");

        LOG.info("Count number of answers the user with id: {} has", userID);

        return this.answerRepository.countNumberOfAnswersOfUser(userID);
    }

    public Long countNumberOfAnswersOfQuestion(final Long questionID) throws NullPointerException {
        notNull(questionID, "questionID cannot be null");

        LOG.info("Count number of answers the question with id: {} has", questionID);

        return this.answerRepository.countNumberOfAnswersOfQuestion(questionID);
    }

    public void removeAnswer(final Long id) throws AnswerNotFoundException {
        notNull(id, "id cannot be null");

        LOG.info("Remove answer by id: {}", id);

        this.answerRepository.removeAnswer(id);
    }
}
