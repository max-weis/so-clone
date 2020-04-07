package main.de.maxwell.qa.answer.domain;

import de.maxwell.qa.domain.answer.Answer;
import de.maxwell.qa.domain.answer.AnswerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class AnswerBuilderTest {

    private AnswerBuilder builder;

    @BeforeEach
    public void setUp() {
        this.builder = new AnswerBuilder();
    }

    @Test
    public void testBuild() {
        LocalDateTime now = LocalDateTime.now();

        Answer answer = builder
                .withUserID("0")
                .withQuestionID(0L)
                .withDescription("test")
                .withModifiedAt(now)
                .withCreatedAt(now)
                .build();

        assertThat(answer.getUserID()).isEqualTo("0");
        assertThat(answer.getQuestionID()).isEqualTo(0L);
        assertThat(answer.getDescription()).isEqualTo("test");
        assertThat(answer.getRating()).isEqualTo(0L);
        assertThat(answer.getCorrectAnswer()).isEqualTo(false);
        assertThat(answer.getCreatedAt()).isEqualTo(now);
        assertThat(answer.getModifiedAt()).isEqualTo(now);
    }

    @Test
    public void testBuildShouldFailForNullUserID() {
        assertThatNullPointerException().isThrownBy(() -> builder.withUserID(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForNullQuestionID() {
        assertThatNullPointerException().isThrownBy(() -> builder.withQuestionID(null)
                .build());
    }

    @Test
    public void testBuildShouldFailForNullRating() {
        assertThatNullPointerException().isThrownBy(() -> builder.withRating(null)
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
    public void testBuildShouldFailForNullCorrectAnswer() {
        assertThatNullPointerException().isThrownBy(() -> builder.withCorrectAnswer(null)
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
