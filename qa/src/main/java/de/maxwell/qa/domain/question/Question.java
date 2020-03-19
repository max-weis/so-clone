package de.maxwell.qa.domain.question;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAB_QUESTION")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COL_QUESTION_ID", nullable = false)
    private Long id;

    @Column(name = "COL_USER_ID", nullable = false)
    private Long userID;

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

    public static QuestionBuilder newBuilder(){
        return new QuestionBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(final Long userID) {
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

}
