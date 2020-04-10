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

package main.de.maxwell.qa.comment.domain;

import de.maxwell.qa.domain.comment.Comment;
import de.maxwell.qa.domain.comment.CommentBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommentBuilderTest {

    private CommentBuilder builder;

    @BeforeEach
    public void setUp() {
        this.builder = new CommentBuilder();
    }

    @Test
    public void testBuildWithQuestionID() {
        LocalDateTime now = LocalDateTime.now();

        Comment comment = builder
                .withUserID("0")
                .withDescription("test")
                .withQuestionID(1L)
                .withModifiedAt(now)
                .withCreatedAt(now)
                .build();

        assertThat(comment.getUserID()).isEqualTo("0");
        assertThat(comment.getQuestionID()).isEqualTo(1L);
        assertThat(comment.getDescription()).isEqualTo("test");
        assertThat(comment.getRating()).isEqualTo(0L);
        assertThat(comment.getCreatedAt()).isEqualTo(now);
        assertThat(comment.getModifiedAt()).isEqualTo(now);
    }

    @Test
    public void testBuildWithAnswerID() {
        LocalDateTime now = LocalDateTime.now();

        Comment comment = builder
                .withUserID("0")
                .withDescription("test")
                .withAnswerID(1L)
                .withModifiedAt(now)
                .withCreatedAt(now)
                .build();

        assertThat(comment.getUserID()).isEqualTo("0");
        assertThat(comment.getAnswerID()).isEqualTo(1L);
        assertThat(comment.getDescription()).isEqualTo("test");
        assertThat(comment.getRating()).isEqualTo(0L);
        assertThat(comment.getCreatedAt()).isEqualTo(now);
        assertThat(comment.getModifiedAt()).isEqualTo(now);
    }

    @Test
    public void testBuildWithQuestionIDAndAnswerID() {
        assertThatThrownBy(() -> builder.withAnswerID(1L)
                .withQuestionID(1L)
                .build()).isInstanceOf(IllegalStateException.class)
                .hasMessage("QuestionID and AnswerID cannot be set at once");
    }

    @Test
    public void testBuildWithNoQuestionIDOrAnswerID() {
        assertThatThrownBy(() -> builder.withAnswerID(null)
                .withQuestionID(null)
                .build()).isInstanceOf(IllegalStateException.class)
                .hasMessage("QuestionID or AnswerID has to be set");
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
