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

import java.time.LocalDateTime;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class QuestionBuilder {

    private Question question;

    public QuestionBuilder() {
        this.question = new Question();
    }

    public QuestionBuilder withUserID(final String userID) {
        notNull(userID, "userId cannot be null");
        this.question.setUserID(userID);
        return this;
    }

    public QuestionBuilder withRating(final Long rating) {
        notNull(rating, "rating cannot be null");
        this.question.setRating(rating);
        return this;
    }

    public QuestionBuilder withTitle(final String title) {
        notNull(title, "title cannot be null");
        notEmpty(title, "title cannot be empty");
        this.question.setTitle(title);
        return this;
    }

    public QuestionBuilder withDescription(final String description) {
        notNull(description, "description cannot be null");
        notEmpty(description, "description cannot be empty");
        this.question.setDescription(description);
        return this;
    }

    public QuestionBuilder withNumberOfAnswers(final Long numberOfAnswers) {
        notNull(numberOfAnswers, "numberOfAnswers cannot be null");
        this.question.setNumberOfAnswers(numberOfAnswers);
        return this;
    }

    public QuestionBuilder withCorrectAnswer(final Long correctAnswer) {
        notNull(correctAnswer, "correctAnswer cannot be null");
        this.question.setCorrectAnswer(correctAnswer);
        return this;
    }

    public QuestionBuilder withViews(final Long views) {
        notNull(views, "views cannot be null");
        this.question.setViews(views);
        return this;
    }

    public QuestionBuilder withCreatedAt(final LocalDateTime createdAt) {
        notNull(createdAt, "createdAt cannot be null");
        this.question.setCreatedAt(createdAt);
        return this;
    }

    public QuestionBuilder withModifiedAt(final LocalDateTime modifiedAt) {
        notNull(modifiedAt, "modifiedAt cannot be null");
        this.question.setModifiedAt(modifiedAt);
        return this;
    }

    public Question build() {
        return this.question;
    }

}
