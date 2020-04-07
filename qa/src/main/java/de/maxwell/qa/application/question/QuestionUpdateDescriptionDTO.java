package de.maxwell.qa.application.question;

public class QuestionUpdateDescriptionDTO {
    private Long id;

    private String description;

    public QuestionUpdateDescriptionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
