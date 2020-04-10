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

package main.de.maxwell.qa.answer.domain;

import de.maxwell.qa.domain.answer.Answer;
import de.maxwell.qa.domain.answer.AnswerNotFoundException;
import de.maxwell.qa.domain.answer.AnswerRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import main.de.maxwell.qa.DatabaseResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnswerRepositoryIT {
    private static final Logger LOG = LoggerFactory.getLogger(AnswerRepositoryIT.class);

    @Inject
    AnswerRepository answerRepository;

    @BeforeAll
    public void setUp() {
        answerRepository.createAnswer("1", 1L, "test1");
        answerRepository.createAnswer("2", 1L, "test2");
        answerRepository.createAnswer("3", 1L, "test3");
        answerRepository.createAnswer("4", 2L, "test4");
        answerRepository.createAnswer("5", 2L, "test5");
        answerRepository.createAnswer("6", 2L, "test6");
        answerRepository.createAnswer("7", 3L, "test7");
    }

    @Test
    public void testFindById() {
        Answer answer = answerRepository.findById(1L);

        assertThat(answer.getUserID()).isEqualTo("1");
        assertThat(answer.getQuestionID()).isEqualTo(1L);
        assertThat(answer.getDescription()).isEqualTo("test1");
    }

    @Test
    public void testFindByIdNotFound() {
        assertThatThrownBy(() -> answerRepository.findById(99L)).isInstanceOf(AnswerNotFoundException.class)
                .hasMessageContaining("Could not find answer with id 99");
    }

    @Test
    public void testListAllPaginated() {
        List<Answer> answers = answerRepository.listAllPaginated(5, 0);

        assertThat(answers.size()).isEqualTo(5);
    }

    @Test
    public void testUpdateDescription() {
        Answer answer = answerRepository.updateDescription(2L, "new Description");

        assertThat(answer.getDescription()).isEqualTo("new Description");
    }

    @Test
    public void testUpdateDescriptionNotFound() {
        assertThatThrownBy(() -> answerRepository.updateDescription(99L, "new Description")).isInstanceOf(AnswerNotFoundException.class)
                .hasMessageContaining("Could not find answer with id 99");
    }

    @Test
    public void testUpdateRatingPositive() {
        Long rating = answerRepository.updateRating(4L, 1);

        assertThat(rating).isEqualTo(1L);
    }

    @Test
    public void testUpdateRatingNegative() {
        Long rating = answerRepository.updateRating(5L, -1);

        assertThat(rating).isEqualTo(-1L);
    }

    @Test
    public void testSetCorrectAnswer() {
        Boolean answer = answerRepository.setCorrectAnswer(6L, true);

        assertThat(answer).isTrue();
    }

    @Test
    public void testSetCorrectAnswerNotFound() {
        assertThatThrownBy(() -> answerRepository.setCorrectAnswer(99L, false)).isInstanceOf(AnswerNotFoundException.class)
                .hasMessageContaining("Could not find answer with id 99");
    }

    @Test
    public void testCountNumberOfAnswersOfUser() {
        Long count = answerRepository.countNumberOfAnswersOfUser("1");

        assertThat(count).isEqualTo(1L);
    }

    @Test
    public void testCountNumberOfAnswersOfUserIsZero() {
        Long count = answerRepository.countNumberOfAnswersOfUser("99");

        assertThat(count).isEqualTo(0L);
    }

    @Test
    public void testCountNumberOfAnswersOfQuestion() {
        Long count = answerRepository.countNumberOfAnswersOfQuestion(1L);

        assertThat(count).isEqualTo(3L);
    }

    @Test
    public void testCountNumberOfAnswersOfQuestionIsZero() {
        Long count = answerRepository.countNumberOfAnswersOfQuestion(99L);

        assertThat(count).isEqualTo(0L);
    }

    @Test
    @Order(1)
    public void testDelete() {
        answerRepository.removeAnswer(7L);
        assertThatThrownBy(() -> answerRepository.findById(7L)).isInstanceOf(AnswerNotFoundException.class)
                .hasMessageContaining("Could not find answer with id 7");
    }

    @Test
    public void testDeleteNotFound() {
        assertThatThrownBy(() -> answerRepository.removeAnswer(99L)).isInstanceOf(AnswerNotFoundException.class)
                .hasMessageContaining("Could not find answer with id 99");
    }
}
