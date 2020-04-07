package de.maxwell.qa.application.question;

public class QuestionNewDTO {
    private String userID;

    private String title;

    private String description;

    public QuestionNewDTO() {
    }

    public QuestionNewDTO(final String userID, final String title, final String description) {
        this.userID = userID;
        this.title = title;
        this.description = description;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(final String userID) {
        this.userID = userID;
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
}
