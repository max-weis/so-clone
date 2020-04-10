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

        Question question = builder.withUserID("0")
                .withTitle("test")
                .withDescription("test")
                .withCorrectAnswer(1L)
                .withCreatedAt(now)
                .withModifiedAt(now)
                .build();

        assertThat(question.getUserID()).isEqualTo("0");
        assertThat(question.getTitle()).isEqualTo("test");
        assertThat(question.getDescription()).isEqualTo("test");
        assertThat(question.getNumberOfAnswers()).isEqualTo(0L);
        assertThat(question.getCorrectAnswer()).isEqualTo(1L);
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
