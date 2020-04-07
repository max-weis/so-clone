package de.maxwell.qa.application.question;

public class QuestionUpdateTitleDTO {
    private Long id;

    private String title;

    public QuestionUpdateTitleDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
