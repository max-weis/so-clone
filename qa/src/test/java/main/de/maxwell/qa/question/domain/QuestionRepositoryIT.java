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
import de.maxwell.qa.domain.question.QuestionNotFoundException;
import de.maxwell.qa.domain.question.QuestionRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import main.de.maxwell.qa.DatabaseResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionRepositoryIT {

    @Inject
    QuestionRepository questionRepository;

    @BeforeAll
    public void setUp() {
        questionRepository.createQuestion("1", "test1", "test1");
        questionRepository.createQuestion("1", "test3", "test2");
        questionRepository.createQuestion("1", "test3", "test3");
        questionRepository.createQuestion("1", "test4", "test4");
        questionRepository.createQuestion("1", "test5", "test5");
        questionRepository.createQuestion("1", "test6", "test6");
        questionRepository.createQuestion("1", "test7", "test7");
    }

    @Test
    public void testFindById() {
        Question question = questionRepository.findById(1L);

        assertThat(question.getUserID()).isEqualTo("1");
        assertThat(question.getTitle()).isEqualTo("test1");
        assertThat(question.getDescription()).isEqualTo("test1");
    }

    @Test
    public void testFindByIdNotFound() {
        assertThatThrownBy(() -> questionRepository.findById(99L)).isInstanceOf(QuestionNotFoundException.class)
                .hasMessageContaining("Could not find question with id 99");
    }

    @Test
    public void testListAllPaginated() {
        List<Question> questions = questionRepository.listAllPaginated(5, 0);

        assertThat(questions.size()).isEqualTo(5);
    }

    @Test
    public void testUpdateTitle() {
        Question question = questionRepository.updateTitle(2L, "new Title");

        assertThat(question.getTitle()).isEqualTo("new Title");
    }

    @Test
    public void testUpdateTitleNotFound() {
        assertThatThrownBy(() -> questionRepository.updateTitle(99L, "new Title")).isInstanceOf(QuestionNotFoundException.class)
                .hasMessageContaining("Could not find question with id 99");
    }

    @Test
    public void testUpdateDescription() {
        Question question = questionRepository.updateDescription(2L, "new Description");

        assertThat(question.getDescription()).isEqualTo("new Description");
    }

    @Test
    public void testUpdateDescriptionNotFound() {
        assertThatThrownBy(() -> questionRepository.updateDescription(99L, "new Description")).isInstanceOf(QuestionNotFoundException.class)
                .hasMessageContaining("Could not find question with id 99");
    }

    @Test
    public void testIncrementView() {
        Long view = questionRepository.incrementView(3L);

        assertThat(view).isEqualTo(1);
    }

    @Test
    public void testIncrementViewNotFound() {
        assertThatThrownBy(() -> questionRepository.incrementView(99L)).isInstanceOf(QuestionNotFoundException.class)
                .hasMessageContaining("Could not find question with id 99");
    }

    @Test
    public void testUpdateRatingPositive() {
        Long rating = questionRepository.updateRating(4L, 1);

        assertThat(rating).isEqualTo(1L);
    }

    @Test
    public void testUpdateRatingNegative() {
        Long rating = questionRepository.updateRating(5L, -1);

        assertThat(rating).isEqualTo(-1L);
    }

    @Test
    public void testSetCorrectAnswer() {
        Long correctAnswer = questionRepository.setCorrectAnswer(6L, 1L);

        assertThat(correctAnswer).isEqualTo(1L);
    }

    @Test
    public void testSetCorrectAnswerNotFound() {
        assertThatThrownBy(() -> questionRepository.setCorrectAnswer(99L, 1L)).isInstanceOf(QuestionNotFoundException.class)
                .hasMessageContaining("Could not find question with id 99");
    }

    @Test
    public void testCountNumberOfAnswersOfUser() {
        Long count = questionRepository.countNumberOfQuestionsOfUser("1");

        assertThat(count).isEqualTo(6L);
    }

    @Test
    public void testCountNumberOfAnswersOfUserIsZero() {
        Long count = questionRepository.countNumberOfQuestionsOfUser("99");

        assertThat(count).isEqualTo(0L);
    }

    @Test
    @Order(1)
    public void testDelete() {
        questionRepository.removeQuestion(7L);
        assertThatThrownBy(() -> questionRepository.findById(7L)).isInstanceOf(QuestionNotFoundException.class)
                .hasMessageContaining("Could not find question with id 7");
    }

    @Test
    public void testDeleteNotFound() {
        assertThatThrownBy(() -> questionRepository.removeQuestion(99L)).isInstanceOf(QuestionNotFoundException.class)
                .hasMessageContaining("Could not find question with id 99");
    }
}
