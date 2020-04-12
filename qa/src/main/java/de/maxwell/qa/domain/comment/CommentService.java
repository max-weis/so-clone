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

package de.maxwell.qa.domain.comment;

import de.maxwell.qa.infrastructure.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class CommentService {
    private static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    @Inject
    CommentRepository commentRepository;

    public Comment findComment(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("Find comment by id: {}", id);

        return this.commentRepository.findById(id);
    }

    public List<Comment> listCommentsPaginatedByQuestionID(final Long questionID, final Integer limit, final Integer offset) {
        notNull(questionID, "questionID cannot be null");
        notNull(limit, "limit cannot be null");
        notNull(offset, "offset cannot be null");

        LOG.info("Find {} comments by question id", limit * offset);

        return this.commentRepository.listAllPaginatedByQuestionID(questionID, limit, offset);
    }

    public List<Comment> listCommentsByQuestionID(final Long questionID) {
        notNull(questionID, "questionID cannot be null");

        LOG.info("Find comments by question id: {}", questionID);

        return this.commentRepository.listAllByQuestionID(questionID);
    }

    public List<Comment> listCommentsPaginatedByAnswerID(final Long answerID, final Integer limit, final Integer offset) {
        notNull(answerID, "answerID cannot be null");
        notNull(limit, "limit cannot be null");
        notNull(offset, "offset cannot be null");

        LOG.info("Find {} comments by answer id", limit * offset);

        return this.commentRepository.listAllPaginatedByAnswerID(answerID, limit, offset);
    }

    public List<Comment> listCommentsByAnswerID(final Long answerID) {
        notNull(answerID, "answerID cannot be null");

        LOG.info("Find comments by answer id: {}", answerID);

        return this.commentRepository.listAllByAnswerID(answerID);
    }

    public Comment createComment(final String userID, final Long questionID, final Long answerID, final String description) {
        notNull(userID, "userID cannot be null");
        notNull(questionID, "questionID cannot be null");
        notNull(answerID, "answerID cannot be null");
        notNull(description, "description cannot be null");

        notEmpty(description, "description cannot be empty");

        LOG.info("Create new Answer");

        return this.commentRepository.createComment(userID, questionID, answerID, description);
    }

    public Comment updateDescription(final Long id, final String newDescription) {
        notNull(id, "id cannot be null");
        notNull(newDescription, "newDescription cannot be null");

        notEmpty(newDescription, "newDescription cannot be empty");

        LOG.info("Update Description of comment with id: {}", id);

        return this.commentRepository.updateDescription(id, newDescription);
    }

    public Long decrementRating(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("Decrement Rating of comment with id: {}", id);

        return this.commentRepository.updateRating(id, -1);
    }

    public Long incrementRating(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("Increment Rating of comment with id: {}", id);

        return this.commentRepository.updateRating(id, 1);
    }

    public void removeComment(final Long id) {
        notNull(id, "id cannot be null");

        LOG.info("Remove comment with id: {}", id);

        this.commentRepository.removeComment(id);
    }
}
