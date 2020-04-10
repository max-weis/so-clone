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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TAB_QUESTION")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COL_ID", nullable = false)
    private Long id;

    @Column(name = "COL_USER_ID", nullable = false)
    private String userID;

    @Column(name = "COL_RATING")
    private Long rating;

    @Column(name = "COL_TITLE", nullable = false)
    private String title;

    @Column(name = "COL_DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "COL_NUM_ANSWER")
    private Long numberOfAnswers;

    @Column(name = "COL_CORRECT_ANSWER")
    private Long correctAnswer;

    @Column(name = "COL_VIEWS")
    private Long views;

    @Column(name = "COL_CREATED", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "COL_MODIFIED", nullable = false)
    private LocalDateTime modifiedAt;

    public Question() {
        LocalDateTime now = LocalDateTime.now();

        this.rating = 0L;
        this.numberOfAnswers = 0L;
        this.views = 0L;

        this.createdAt = now;
        this.modifiedAt = now;
    }

    public static QuestionBuilder newBuilder() {
        return new QuestionBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(final String userID) {
        this.userID = userID;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(final Long rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(final Long numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    public Long getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(final Long correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(final Long views) {
        this.views = views;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(final LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", userID=" + userID +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", numberOfAnswers=" + numberOfAnswers +
                ", correctAnswer=" + correctAnswer +
                ", views=" + views +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
