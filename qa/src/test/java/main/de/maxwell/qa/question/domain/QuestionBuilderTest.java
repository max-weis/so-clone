package main.de.maxwell.qa.question.domain;

import de.maxwell.qa.domain.question.Question;
import de.maxwell.qa.domain.question.QuestionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class QuestionBuilderTest {

    private QuestionBuilder builder;

    @BeforeEach
    public void setUp() {
        this.builder = new QuestionBuilder();
    }

    @Test
    public void testBuild() {
        LocalDateTime now = LocalDateTime.now();

        Question question = builder.withUserID(0L)
                .withRating(0L)
                .withTitle("test")
                .withDescription("test")
                .withNumberOfAnswers(0L)
                .withCorrectAnswer(0L)
                .withViews(0L)
                .withCreatedAt(now)
                .withModifiedAt(now)
                .build();

        assertThat(question.getUserID()).isEqualTo(0L);
        assertThat(question.getTitle()).isEqualTo("test");
        assertThat(question.getDescription()).isEqualTo("test");
        assertThat(question.getNumberOfAnswers()).isEqualTo(0L);
        assertThat(question.getCorrectAnswer()).isEqualTo(0L);
        assertThat(question.getViews()).isEqualTo(0L);
        assertThat(question.getCreatedAt()).isEqualTo(now);
        assertThat(question.getModifiedAt()).isEqualTo(now);
    }

    @Test
    public void testBuildShouldFailForNullUserID() {
        assertThatNullPointerException().isThrownBy(() -> builder.withUserID(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForNullRating() {
        assertThatNullPointerException().isThrownBy(() -> builder.withRating(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForEmptyTitle() {
        assertThatIllegalArgumentException().isThrownBy(() -> builder.withTitle("")
                .build());
    }

    @Test
    public void testBuildShouldFailForNullTitle() {
        assertThatNullPointerException().isThrownBy(() -> builder.withTitle(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForEmptyDescription() {
        assertThatIllegalArgumentException().isThrownBy(() -> builder.withDescription("")
                .build());
    }

    @Test
    public void testBuildShouldFailForNullDescription() {
        assertThatNullPointerException().isThrownBy(() -> builder.withDescription(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForNullNumberOfAnswers() {
        assertThatNullPointerException().isThrownBy(() -> builder.withNumberOfAnswers(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForNullCorrectAnswer() {
        assertThatNullPointerException().isThrownBy(() -> builder.withCorrectAnswer(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForNullViews() {
        assertThatNullPointerException().isThrownBy(() -> builder.withViews(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForNullCreatedAt() {
        assertThatNullPointerException().isThrownBy(() -> builder.withCreatedAt(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForNullModifiedAt() {
        assertThatNullPointerException().isThrownBy(() -> builder.withModifiedAt(null)
                .build());
    }
}