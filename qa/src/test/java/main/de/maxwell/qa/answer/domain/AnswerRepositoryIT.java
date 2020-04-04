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
        answerRepository.createAnswer(1L, "test1");
        answerRepository.createAnswer(2L, "test2");
        answerRepository.createAnswer(3L, "test3");
        answerRepository.createAnswer(4L, "test4");
        answerRepository.createAnswer(5L, "test5");
        answerRepository.createAnswer(6L, "test6");
        answerRepository.createAnswer(7L, "test7");
    }

    @Test
    public void testFindById() {
        Answer answer = answerRepository.findById(1L);

        assertThat(answer.getUserID()).isEqualTo(1L);
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
        Answer answers = answerRepository.updateDescription(2L, "new Description");

        assertThat(answers.getDescription()).isEqualTo("new Description");
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