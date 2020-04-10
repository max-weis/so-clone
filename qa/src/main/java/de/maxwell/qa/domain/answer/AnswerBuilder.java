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

import java.time.LocalDateTime;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class AnswerBuilder {

    private Answer answer;

    public AnswerBuilder() {
        answer = new Answer();
    }

    public AnswerBuilder withUserID(final String userID) {
        notNull(userID, "userId cannot be null");
        this.answer.setUserID(userID);
        return this;
    }

    public AnswerBuilder withQuestionID(final Long questionID) {
        notNull(questionID, "questionID cannot be null");
        this.answer.setQuestionID(questionID);
        return this;
    }

    public AnswerBuilder withRating(final Long rating) {
        notNull(rating, "rating cannot be null");
        this.answer.setRating(rating);
        return this;
    }

    public AnswerBuilder withDescription(final String description) {
        notNull(description, "description cannot be null");
        notEmpty(description, "description cannot be empty");
        this.answer.setDescription(description);
        return this;
    }

    public AnswerBuilder withCorrectAnswer(final Boolean correct) {
        notNull(correct, "correct cannot be null");
        this.answer.setCorrectAnswer(correct);
        return this;
    }

    public AnswerBuilder withCreatedAt(final LocalDateTime createdAt) {
        notNull(createdAt, "createdAt cannot be null");
        this.answer.setCreatedAt(createdAt);
        return this;
    }

    public AnswerBuilder withModifiedAt(final LocalDateTime modifiedAt) {
        notNull(modifiedAt, "modifiedAt cannot be null");
        this.answer.setModifiedAt(modifiedAt);
        return this;
    }

    public Answer build() {
        return this.answer;
    }
}
