package main.de.maxwell.qa.comment.domain;

import de.maxwell.qa.domain.comment.Comment;
import de.maxwell.qa.domain.comment.CommentBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class CommentBuilderTest {

    private CommentBuilder builder;

    @BeforeEach
    public void setUp() {
        this.builder = new CommentBuilder();
    }

    @Test
    public void testBuild() {
        LocalDateTime now = LocalDateTime.now();

        Comment comment = builder
                .withUserID("0")
                .withDescription("test")
                .withModifiedAt(now)
                .withCreatedAt(now)
                .build();

        assertThat(comment.getUserID()).isEqualTo("0");
        assertThat(comment.getDescription()).isEqualTo("test");
        assertThat(comment.getRating()).isEqualTo(0L);
        assertThat(comment.getCreatedAt()).isEqualTo(now);
        assertThat(comment.getModifiedAt()).isEqualTo(now);
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
