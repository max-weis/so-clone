package de.maxwell.qa.domain.answer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TAB_ANSWER")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COL_ID", nullable = false)
    private Long id;

    @Column(name = "COL_USER_ID", nullable = false)
    private String userID;

    @Column(name = "COL_QUESTION_ID", nullable = false)
    private Long questionID;

    @Column(name = "COL_DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "COL_RATING")
    private Long rating;

    @Column(name = "COL_CORRECT_ANSWER")
    private Boolean correctAnswer;

    @Column(name = "COL_CREATED", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "COL_MODIFIED", nullable = false)
    private LocalDateTime modifiedAt;

    public Answer() {
        LocalDateTime now = LocalDateTime.now();

        this.rating = 0L;
        this.correctAnswer = false;

        this.createdAt = now;
        this.modifiedAt = now;
    }

    public static AnswerBuilder newBuilder() {
        return new AnswerBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
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

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", userID=" + userID +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", correctAnswer=" + correctAnswer +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
