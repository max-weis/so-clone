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

package de.maxwell.qa.domain.question;

import de.maxwell.qa.domain.answer.Answer;
import de.maxwell.qa.domain.answer.AnswerRepository;
import de.maxwell.qa.infrastructure.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class QuestionService {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionService.class);

    @Inject
    QuestionRepository questionRepository;

    @Inject
    AnswerRepository answerRepository;

    public Question findQuestion(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("Find Question with ID: {}", id);

        return questionRepository.findById(id);
    }

    public List<Question> findQuestions(final Integer limit, final Integer offset) {
        notNull(limit, "limit cannot be null");
        notNull(offset, "offset cannot be null");

        LOG.info("Find {} questions", limit * (offset + 1));

        return this.questionRepository.listAllPaginated(limit, offset);
    }

    public Question createQuestion(final String userID, final String title, final String description) {
        notNull(userID, "userID cannot be null");
        notNull(title, "title cannot be null");
        notNull(description, "description cannot be null");

        notEmpty(userID, "userID cannot be empty");
        notEmpty(title, "title cannot be empty");
        notEmpty(description, "description cannot be empty");

        LOG.info("Create new Question");

        return questionRepository.createQuestion(userID, title, description);
    }

    public Question updateTitle(final Long id, final String title) {
        notNull(id, "id cannot be null");

        notNull(title, "title cannot be null");
        notEmpty(title, "title cannot be empty");

        LOG.info("Update title of Question with id: {}", id);

        return this.questionRepository.updateTitle(id, title);
    }

    public Question updateDescription(final Long id, final String description) {
        notNull(id, "id cannot be null");

        notNull(description, "description cannot be null");
        notEmpty(description, "description cannot be empty");

        LOG.info("Update description of Question with id: {}", id);

        return this.questionRepository.updateDescription(id, description);
    }

    public Long incrementView(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("increment view of Question with id: {}", id);

        return this.questionRepository.incrementView(id);
    }

    public Long upvoteRating(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("Upvote Rating with id: {}", id);

        return questionRepository.updateRating(id, 1);
    }

    public Long downvoteRating(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("Downvote Rating with id: {}", id);

        return questionRepository.updateRating(id, -1);
    }

    public Long setCorrectAnswer(final Long questionId, final Long answerId) {
        notNull(questionId, "questionID cannot be null");
        notNull(answerId, "answerId cannot be null");

        Answer answer = this.answerRepository.findById(answerId);

        LOG.info("Set correct answer of question with id: {}", questionId);
        return this.questionRepository.setCorrectAnswer(questionId, answer.getId());
    }

    public Long getCount(final String userID) {

        notNull(userID, "userId cannot be null");
        notEmpty(userID, "userId cannot be empty");

        LOG.info("Get number of question of user with ID: {}", userID);

        return questionRepository.countNumberOfQuestionsOfUser(userID);
    }

    public void removeQuestion(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("Delete question with id: {}", id);

        questionRepository.removeQuestion(id);
    }
}
