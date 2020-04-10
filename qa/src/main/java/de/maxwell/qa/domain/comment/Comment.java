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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

//TODO: add question and answer id
@Entity
@Table(name = "TAB_COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COL_ID", nullable = false)
    private Long id;

    @Column(name = "COL_USER_ID", nullable = false)
    private String userID;

    @Column(name = "COL_QUESTION_ID")
    private Long questionID;

    @Column(name = "COL_ANSWER_ID", nullable = false)
    private Long answerID;

    @Column(name = "COL_DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "COL_RATING")
    private Long rating;

    @Column(name = "COL_CREATED", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "COL_MODIFIED", nullable = false)
    private LocalDateTime modifiedAt;

    public Comment() {
        LocalDateTime now = LocalDateTime.now();

        this.rating = 0L;

        this.createdAt = now;
        this.modifiedAt = now;
    }

    public static CommentBuilder newBuilder() {
        return new CommentBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(final Long questionID) {
        this.questionID = questionID;
    }

    public Long getAnswerID() {
        return answerID;
    }

    public void setAnswerID(final Long answerID) {
        this.answerID = answerID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
